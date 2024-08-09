package com.manoffocus.common.data.di

import android.content.Context
import com.manoffocus.common.data.database.ChatAppDatabase
import com.manoffocus.common.data.database.ConversationDao
import com.manoffocus.common.data.database.MessageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ChatAppDatabase {
        return ChatAppDatabase.getDatabase(appContext)
    }

    @Provides
    fun provideMessageDao(database: ChatAppDatabase): MessageDao {
        return database.messageDao()
    }

    @Provides
    fun provideConversationDao(database: ChatAppDatabase): ConversationDao {
        return database.conversationDao()
    }
}