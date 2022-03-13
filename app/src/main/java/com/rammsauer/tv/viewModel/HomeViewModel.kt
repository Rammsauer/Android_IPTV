package com.rammsauer.tv.viewModel

import androidx.lifecycle.*
import com.rammsauer.tv.repository.ChannelRepository
import com.rammsauer.tv.data.database.Channel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val channelRepository: ChannelRepository
): ViewModel() {
    private val _channel: MutableLiveData<List<Channel>> by lazy {
        MutableLiveData<List<Channel>>().also {
            updateChannel()
        }
    }

    val group: LiveData<List<String>> = channelRepository.getGroup().asLiveData()


    private fun updateChannel() {
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