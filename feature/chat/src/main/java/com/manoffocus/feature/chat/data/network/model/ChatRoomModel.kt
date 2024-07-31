package com.manoffocus.feature.chat.data.network.model

import com.manoffocus.feature.chat.domain.models.ChatRoom
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomModel(
    val id: String,
    val senderName: String,
    val senderAvatar: String,
    val lastMessages: List<WebsocketMessageModel>
) {
    fun toDomain(): ChatRoom {
        return ChatRoom(
            id = id,
            senderName = senderName,
            senderAvatar = senderAvatar,
            lastMessages = lastMessages.map { it.toDomain() }
        )
    }
}