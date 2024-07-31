package com.manoffocus.feature.chat.domain.usecases


import com.manoffocus.feature.chat.domain.IChatRoomRepository
import com.manoffocus.feature.chat.domain.models.ChatRoom
import javax.inject.Inject

class GetInitialChatRoomInformation @Inject constructor(
    private val repository: IChatRoomRepository
) {
    suspend operator fun invoke(id: String): ChatRoom {
        return repository.getInitialChatRoom(id)
    }
}