package com.rammsauer.tv.ViewModel

import android.util.Log
import androidx.lifecycle.*
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.rammsauer.tv.ChannelRepository
import com.rammsauer.tv.Data.Channel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val channelRepository: ChannelRepository
): ViewModel() {
    private val _channel: MutableLiveData<List<Channel>> by lazy {
        MutableLiveData<List<Channel>>().also {
            setChannel()
            updateChannel()
        }
    }

    val group: LiveData<List<String>> = channelRepository.getGroup().asLiveData()

    fun setChannel() = viewModelScope.launch {
        channelRepository.setChannels()
    }

    fun updateChannel() {
        viewModelScope.launch {
            val value = channelRepository.getChannels()
            _channel.postValue(value)
        }
    }

    fun getChannel(): LiveData<List<Channel>> {
        return _channel
    }

    fun changeCountry(country: String){
        viewModelScope.launch {
            val value = channelRepository.getChannelsFromCountry(country)
            _channel.postValue(value)
        }
    }
}