package com.manoffocus.feature.chat.domain

import com.manoffocus.feature.chat.domain.models.ChatRoom

interface IChatRoomRepository {
    suspend fun getInitialChatRoom(id: String): ChatRoom
}