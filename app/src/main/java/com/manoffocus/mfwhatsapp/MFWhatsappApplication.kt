package com.manoffocus.mfwhatsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MFWhatsappApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
    companion object {
        const val BASE_URI = "mfwhatsapp.com"
    }
}