package com.rammsauer.tv.screen

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rammsauer.tv.screen.homeScreen.HomeScreen
import com.rammsauer.tv.viewModel.EntryScreenViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EntryScreen(
    viewModel: EntryScreenViewModel = viewModel()
){
    val isLoading by viewModel.isLoading.observeAsState(true)
    viewModel.initChannelList()

    LoadingScreen(
        isLoading = isLoading
    ) {
        HomeScreen()
    }
}