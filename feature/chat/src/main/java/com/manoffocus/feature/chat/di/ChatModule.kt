package com.manoffocus.feature.chat.di

import android.content.Context
import com.manoffocus.feature.chat.data.network.WebsocketClient
import com.manoffocus.feature.chat.data.network.RestClient
import com.manoffocus.feature.chat.data.network.datasource.FirebaseFirestoreProvider
import com.manoffocus.feature.chat.data.network.repository.ChatRoomRepository
import com.manoffocus.feature.chat.data.network.repository.MessagesRepository
import com.manoffocus.feature.chat.domain.IChatRoomRepository
import com.manoffocus.feature.chat.domain.IMessagesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ChatModule {

    companion object {
        const val WEBSOCKET_URL = "ws://mfwhatsapp.com/chat/%s"
        const val WEBSOCKET_URL_NAME = "WEBSOCKET_URL"
        const val WEBSOCKET_CLIENT = "WEBSOCKET_CLIENT"
        const val API_CHAT_ROOM_URL = "http://mfwhatsapp.com/chats/%s"
        const val API_CHAT_ROOM_URL_NAME = "CHATROOM_URL"
        const val API_CLIENT = "API_CLIENT"
    }

    @Provides
    @Named(WEBSOCKET_CLIENT)
    fun providesWebsocketHttpClient(): HttpClient {
        return WebsocketClient.client
    }

    @Provides
    @Named(WEBSOCKET_URL_NAME)
    fun providesWebsocketURL(): String {
        return WEBSOCKET_URL
    }


    @Provides
    @Named(API_CLIENT)
    fun providesAPIHttpClient(): HttpClient {
        return RestClient.client
    }

    @Provides
    @Named(API_CHAT_ROOM_URL_NAME)
    fun providesApiChatUrl(): String {
        return API_CHAT_ROOM_URL
    }
    @Provides
    @Singleton
    fun providesFirebaseFirestoreProvider(@ApplicationContext context: Context): FirebaseFirestoreProvider {
        return FirebaseFirestoreProvider(context = context)
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class ChatBindingsModule {
    @Binds
    abstract fun providesChatRoomRepository(
        chatRoomRepository: ChatRoomRepository
    ): IChatRoomRepository

    @Binds
    abstract fun providesMessagesRepository(
        messagesRepository: MessagesRepository
    ): IMessagesRepository
}