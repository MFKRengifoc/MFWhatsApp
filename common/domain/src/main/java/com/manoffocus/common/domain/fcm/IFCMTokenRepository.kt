package com.manoffocus.common.domain.fcm

interface IFCMTokenRepository {
    suspend fun getFCMToken(): String
}