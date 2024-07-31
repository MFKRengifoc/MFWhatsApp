package com.manoffocus.feature.chat.ui.model

import com.manoffocus.feature.chat.domain.models.ChatRoom

data class Chat(
    val id: String? = null,
    val name: String? = null,
    val avatar: String? = null
)

fun ChatRoom.toUI() = run {
    Chat(
        id = id,
        name = senderName,
        avatar = senderAvatar
    )
}