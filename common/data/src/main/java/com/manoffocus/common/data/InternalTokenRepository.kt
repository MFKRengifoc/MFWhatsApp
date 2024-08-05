package com.manoffocus.common.data

import com.manoffocus.common.domain.fcm.IInternalTokenRepository

class InternalTokenRepository(): IInternalTokenRepository {
    override suspend fun storeToken(userId: String, token: String) {
        // Store in the data source of your choosing
    }
}