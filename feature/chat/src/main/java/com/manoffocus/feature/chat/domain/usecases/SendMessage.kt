package com.manoffocus.feature.chat.domain.usecases

import com.manoffocus.feature.chat.domain.IMessagesRepository
import com.manoffocus.feature.chat.domain.models.Message
import javax.inject.Inject

class SendMessage @Inject constructor(
    private val repository: IMessagesRepository
) {
    suspend operator fun invoke(chatId: String, message: Message) {
        repository.sendMessage(chatId, message)
    }
}