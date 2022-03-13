package com.rammsauer.tv.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rammsauer.tv.repository.ChannelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class EntryScreenViewModel @Inject constructor(
    private val channelRepository: ChannelRepository
): ViewModel() {
    private val _isLoading : MutableLiveData<Boolean> = MutableLiveData(true)
    val isLoading : LiveData<Boolean> = _isLoading

    fun initChannelList() = viewModelScope.launch {
        if (isEmpty()) {
            channelRepository.setChannels(){
                _isLoading.postValue(it)
            }
        }
        else {
            channelRepository.setChannels {}
            _isLoading.postValue(false)
        }
    }

    suspend fun isEmpty(): Boolean {
        return channelRepository.getChannels().isEmpty()
    }
}