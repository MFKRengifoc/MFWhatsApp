package com.manoffocus.feature.chat.data.network.repository

import com.manoffocus.feature.chat.data.network.datasource.ChatRoomDataSource
import com.manoffocus.feature.chat.domain.IChatRoomRepository
import com.manoffocus.feature.chat.domain.models.ChatRoom
import javax.inject.Inject

class ChatRoomRepository @Inject constructor(
    private val dataSource: ChatRoomDataSource
): IChatRoomRepository {
    override suspend fun getInitialChatRoom(id: String): ChatRoom {
        val chatRoomApiModel = dataSource.getInitialChatRoom(id)
        return chatRoomApiModel.toDomain()
    }
}