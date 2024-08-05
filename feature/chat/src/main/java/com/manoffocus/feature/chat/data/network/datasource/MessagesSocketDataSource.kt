package com.manoffocus.feature.chat.data.network.datasource

import android.util.Log
import com.manoffocus.feature.chat.data.network.model.WebsocketMessageModel
import com.manoffocus.feature.chat.di.ChatModule.Companion.WEBSOCKET_CLIENT
import com.manoffocus.feature.chat.di.ChatModule.Companion.WEBSOCKET_URL_NAME
import com.manoffocus.feature.chat.domain.models.Message
import io.ktor.client.*
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.plugins.websocket.converter
import io.ktor.client.request.*
import io.ktor.serialization.*
import io.ktor.utils.io.errors.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Named

class MessagesSocketDataSource @Inject constructor(
    @Named(WEBSOCKET_CLIENT) private val httpClient:
    HttpClient,
    @Named(WEBSOCKET_URL_NAME) private val websocketUrl:
    String
) {
    companion object {
        const val TAG = "MessagesSocketDataSource"
        const val RETRY_DELAY = 10000L
        const val MAX_RETRIES = 5
    }
    private lateinit var _webSocketSession: DefaultClientWebSocketSession
    suspend fun connect(): Flow<Message> {
        return flow {
            try {
                httpClient.webSocketSession { url(websocketUrl) }
                    .apply { _webSocketSession = this }
                    .incoming
                    .receiveAsFlow()
                    .collect { frame ->
                        try {
                            val message = _webSocketSession.handleMessage(
                                    frame)?.toDomain()
                            if (message != null) {
                                emit(message)
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Error handling WebSocket frame", e)
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Error connecting to WebSocket", e)
            }
        }.retryWhen { cause, attempt ->
            if (cause is IOException && attempt < MAX_RETRIES)
            {
                delay(RETRY_DELAY)
                true
            } else {
                false
            }
        }.catch { e ->
            // Handle exceptions from the Flow
            Log.e(TAG, "Error in WebSocket Flow", e)
        }
    }

    suspend fun sendMessage(message: Message) {
        val websocketMessage = WebsocketMessageModel.fromDomain(message)
        _webSocketSession.converter?.serialize(websocketMessage)?.let {
            _webSocketSession.send(it)
        }
    }
    suspend fun disconnect() {
        _webSocketSession.close(CloseReason(CloseReason.Codes.NORMAL, "Disconnect"))
    }

    private suspend fun DefaultClientWebSocketSession.handleMessage(frame: Frame): WebsocketMessageModel? {
        return when (frame) {
            is Frame.Text -> converter?.deserialize(frame)
            is Frame.Close -> {
                disconnect()
                null
            }
            else -> null
        }
    }

}