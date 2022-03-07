package com.rammsauer.tv

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rammsauer.tv.Data.Channel
import com.rammsauer.tv.Data.ChannelDao

@Database(entities = [Channel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun channelDao(): ChannelDao
}
