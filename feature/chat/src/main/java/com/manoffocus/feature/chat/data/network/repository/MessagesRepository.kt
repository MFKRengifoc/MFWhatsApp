package com.manoffocus.feature.chat.data.network.repository


import com.manoffocus.feature.chat.data.network.datasource.FirestoreMessagesDataSource
import com.manoffocus.feature.chat.data.network.datasource.MessagesLocalDataSource
import com.manoffocus.feature.chat.data.network.datasource.MessagesSocketDataSource
import com.manoffocus.feature.chat.domain.IMessagesRepository
import com.manoffocus.feature.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MessagesRepository @Inject constructor(
    private val dataSource: MessagesSocketDataSource,
    private val localDataSource: MessagesLocalDataSource
//    private val dataSource: FirestoreMessagesDataSource
): IMessagesRepository {

//    override suspend fun getMessages(chatId: String, userId: String): Flow<Message> {
//        return dataSource.getMessages(chatId, userId)
//    }

    override suspend fun sendMessage(chatId: String, message: Message) {
        dataSource.sendMessage(message)
    }

    override suspend fun disconnect() {
        //do nothing, Firestore data source is disconnected as soon as the flow has no subscribers
    }

    override suspend fun getMessages(chatId: String, userId:
    String): Flow<Message> {
        return flow {
            try {
                dataSource.connect().collect { message ->
                    localDataSource.insertMessage(message)
                    emit(message)
                    manageDatabaseSize(chatId)
                }
            } catch (e: Exception) {
                localDataSource.getMessagesInConversation(
                    chatId.toInt()).collect {
                    it.forEach { message -> emit(message) }
                }
            }
        }
    }

    private suspend fun manageDatabaseSize(chatId: String) {
        val messages =
            localDataSource.getMessagesInConversation(chatId.toInt()).first()
        if (messages.size > 100) {
            // Delete the oldest messages until we have 100 left
            messages.sortedBy { it.timestamp
            }.take(messages.size - 100).forEach {
                localDataSource.deleteMessage(it)
            }
        }
    }
}