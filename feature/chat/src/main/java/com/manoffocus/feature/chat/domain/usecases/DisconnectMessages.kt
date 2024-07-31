package com.manoffocus.feature.chat.domain.usecases

import com.manoffocus.feature.chat.domain.IMessagesRepository
import javax.inject.Inject

class DisconnectMessages @Inject constructor(
    private val repository: IMessagesRepository
) {
    suspend operator fun invoke() {
        repository.disconnect()
    }
}