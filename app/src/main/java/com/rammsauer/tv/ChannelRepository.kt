package com.rammsauer.tv

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.rammsauer.tv.Data.Channel
import kotlinx.coroutines.flow.Flow
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ChannelRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val network = Network()
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "AppDatabase"
    ).build()
    private val channelDao = db.channelDao()

    fun setChannels(){
        network.getList() { response ->
            M3U_REGEX.findAll(response).iterator().forEach { it ->
                if (channelDao.exists(it.groupValues[4]).isEmpty()) {
                    channelDao.insertAll(
                        Channel(
                            name = it.groupValues[4],
                            url = it.groupValues[10],
                            logo = it.groupValues[6],
                            group = it.groupValues[8],
                        )
                    )

                    network.checkStatus(url = it.groupValues[10]) { status ->
                        channelDao.updateStatus(
                            status = status,
                            url = it.groupValues[10]
                        )
                    }
                    Log.i(javaClass.simpleName, Channel(name = it.groupValues[4], url = it.groupValues[10], logo = it.groupValues[6], group = it.groupValues[8],).toString())
                }
            }
        }
    }

    fun getGroup(): Flow<List<String>> = channelDao.getGroup()

    fun getChannels(): Flow<List<Channel>> = channelDao.getAllOnline()

    fun getChannelsFromCountry(country: String): List<Channel> = channelDao.getChannelCountry(country)

}