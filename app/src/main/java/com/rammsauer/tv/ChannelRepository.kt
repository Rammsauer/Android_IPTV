package com.rammsauer.tv

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.room.Room
import com.rammsauer.tv.Data.Channel
import kotlinx.coroutines.flow.Flow
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.Exception
import java.net.URL
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
            M3U_REGEX.findAll(response).iterator().forEach {
                if (channelDao.exists(it.groupValues[4]).isEmpty()) {
                    channelDao.insertAll(
                        Channel(
                            name    = it.groupValues[4],
                            url     = it.groupValues[10],
                            group   = it.groupValues[8],
                        )
                    )

                    network.checkStatus(url = it.groupValues[10]) { status ->
                        channelDao.updateStatus(
                            status  = status,
                            url     = it.groupValues[10]
                        )
                    }

                    if (it.groupValues[6] != null) {
                        network.getImage(url = URL(it.groupValues[6])) { byteArray ->
                            if (byteArray != null) {
                                channelDao.updateLogo(
                                    url = it.groupValues[10],
                                    logo = byteArray
                                )
                            }
                        }
                    }
                    //Log.i(javaClass.simpleName, Channel(name = it.groupValues[4], url = it.groupValues[10], group = it.groupValues[8],).toString())
                }
            }
        }
    }

    fun getGroup(): Flow<List<String>> = channelDao.getGroup()

    suspend fun getChannels(): List<Channel> = channelDao.getAllOnline()

    suspend fun getChannelsFromCountry(country: String): List<Channel> = channelDao.getChannelCountry(country)
}