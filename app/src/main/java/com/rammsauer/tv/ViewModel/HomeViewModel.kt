package com.rammsauer.tv.ViewModel

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
    private val _channel : MutableLiveData<List<Channel>> = MutableLiveData()
    val channel: LiveData<List<Channel>> = channelRepository.getChannels().asLiveData()

    val group: LiveData<List<String>> = channelRepository.getGroup().asLiveData()

    fun changeCountry(country: String){
        _channel.postValue(channelRepository.getChannelsFromCountry(country))
    }

    fun setChannel() = viewModelScope.launch {
        channelRepository.setChannels()
    }
}