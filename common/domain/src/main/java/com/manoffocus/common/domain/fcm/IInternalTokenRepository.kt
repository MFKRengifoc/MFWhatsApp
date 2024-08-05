package com.manoffocus.common.domain.fcm

interface IInternalTokenRepository {
    suspend fun storeToken(userId: String, token: String)
}