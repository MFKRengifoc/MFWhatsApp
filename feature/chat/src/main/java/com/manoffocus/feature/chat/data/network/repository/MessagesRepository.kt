package com.manoffocus.feature.chat.data.network.repository


import com.manoffocus.feature.chat.domain.IMessagesRepository
import com.manoffocus.feature.chat.domain.models.Message
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessagesRepository @Inject constructor(
    //private val dataSource: MessagesSocketDataSource
): IMessagesRepository {

    override suspend fun getMessages(chatId: String, userId: String): Flow<Message> {
        return TODO()
    }

    override suspend fun sendMessage(chatId: String, message: Message) {
        return TODO()
    }

    override suspend fun disconnect() {
        //do nothing, Firestore data source is disconnected as soon as the flow has no subscribers
    }
}