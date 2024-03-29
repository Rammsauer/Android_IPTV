package com.rammsauer.tv.repository

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import androidx.compose.ui.text.toUpperCase
import androidx.room.Room
import com.rammsauer.tv.data.database.AppDatabase
import com.rammsauer.tv.data.database.Channel
import com.rammsauer.tv.M3U_REGEX
import com.rammsauer.tv.data.Network
import kotlinx.coroutines.flow.Flow
import dagger.hilt.android.qualifiers.ApplicationContext
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

    //TODO GlobalScope.async doesn't work, Callback doesn't look like best practice for this method
    suspend fun setChannels(
        callback: (Boolean) -> Unit
    ) {
        network.getList { response ->
            M3U_REGEX.findAll(response).iterator().forEach {
                if (channelDao.exists(it.groupValues[4]).isEmpty()) {
                    channelDao.insertAll(
                        Channel(
                            name = it.groupValues[4],
                            url = it.groupValues[10],
                            group = it.groupValues[8],
                        )
                    )

                    network.checkStatus(url = it.groupValues[10]) { status ->
                        channelDao.updateStatus(
                            status = status,
                            url = it.groupValues[10]
                        )
                    }

                    network.getImage(url = URL(it.groupValues[6])) { byteArray ->
                        if (byteArray != null) {
                            channelDao.updateLogo(
                                url = it.groupValues[10],
                                logo = byteArray
                            )
                        }
                    }

                    Log.i(
                        javaClass.simpleName, Channel(
                            name = it.groupValues[4],
                            url = it.groupValues[10],
                            group = it.groupValues[8],
                        ).toString()
                    )
                }
            }

            callback(false)
        }
    }


    fun getGroup(): Flow<List<String>> = channelDao.getGroup()

    suspend fun getChannels(): List<Channel> = channelDao.getAllOnline()

    suspend fun getChannelsFromCountry(country: String): List<Channel> = channelDao.getChannelCountry(country)
}