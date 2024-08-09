package com.manoffocus.common.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {

    @Query("SELECT * FROM conversations ORDER BY last_message_time DESC")
    fun getAllConversations(): Flow<List<Conversation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConversation(conversation: Conversation): Long

    @Delete
    fun deleteConversation(conversation: Conversation)

}