package com.rammsauer.tv.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ChannelDao {
    @Query("SELECT * FROM channel")
    suspend fun getAll(): List<Channel>

    @Query("SELECT * FROM channel WHERE onlineStatus=1")
    suspend fun getAllOnline(): List<Channel>

    @Query("SELECT DISTINCT country FROM channel")
    fun getGroup(): Flow<List<String>>

    @Query("SELECT * FROM channel WHERE country=:country")
    suspend fun getChannelCountry(country: String): List<Channel>

    @Query("UPDATE channel SET onlineStatus=:status WHERE url=:url")
    fun updateStatus(status: Int, url: String)

    @Insert
    fun insertAll(channel: Channel)

    @Query("SELECT * FROM channel WHERE name=:name")
    fun exists(name: String): List<Channel>

    @Delete
    suspend fun delete(channel: Channel)

    @Query("DELETE FROM channel")
    suspend fun deleteAll()
}