package com.rammsauer.tv.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Channel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun channelDao(): ChannelDao
}
