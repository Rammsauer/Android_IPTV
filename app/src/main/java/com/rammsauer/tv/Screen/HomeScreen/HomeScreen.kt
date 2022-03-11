package com.rammsauer.tv.Screen.HomeScreen

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.rammsauer.tv.Data.Channel
import com.rammsauer.tv.Screen.HomeScreen.View.CardView
import com.rammsauer.tv.Screen.HomeScreen.View.LogoView
import com.rammsauer.tv.ViewModel.HomeViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val channel: List<Channel> by viewModel.getChannel().observeAsState(initial = emptyList())
    val group by viewModel.group.observeAsState(initial = listOf())
    val context = LocalContext.current

    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            this.setMediaItem(MediaItem.fromUri("https://artesimulcast.akamaized.net/hls/live/2030993/artelive_de/index.m3u8"))
            this.prepare()
        }
    }

    Column() {
        AndroidView(
            factory = {
                //TODO 'PlayerView' is deprecated. found no current alternative
                PlayerView(it).apply {
                    player = exoPlayer
                    this.useController = true
                    this.player!!.play()
                }
            },
            //TODO fillmaxheight not working
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 128.dp, max = 512.dp),
        )

        /*
        DetailView(
            title   = "Countrys",
            country = "",
        ) {
            LazyColumn {
                items(group) {
                    Card(
                       onClick = {
                           viewModel.changeCountry(it)
                       },
                       modifier = Modifier
                           .padding(8.dp)
                    ) {
                        ListItem(
                            text = {
                                Text(text = it)
                            },
                            trailing = {
                                Icon(
                                    imageVector = Icons.Outlined.PlayArrow,
                                    contentDescription = null
                                )
                            })
                    }
                }
            }
        }
        */

        LazyColumn(
            modifier = Modifier.padding(top = 16.dp)
        ){
            items(channel) {
                /*
                DetailView(
                    title       = "${it.name}",
                    country     = "${it.group}",
                    logo        = it.logo
                ) {
                 */
                    CardView(
                        logo    = it.logo,
                        status  = it.status,
                        country = "${it.group}",
                        name    = "${it.name}",
                    ){
                        exoPlayer.stop()
                        exoPlayer.setMediaItem(MediaItem.fromUri("${it.url!!}"))
                        exoPlayer.prepare()
                        exoPlayer.play()
                    }
                //}
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@Composable
fun DetailView(
    title       : String,
    country     : String,
    logo        : ByteArray? = null,
    content     : @Composable () -> Unit)
{
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card (
            onClick = { expanded = !expanded },
            modifier = Modifier.padding(8.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if ((logo != null) && (BitmapFactory.decodeByteArray(logo, 0, logo!!.size) != null)) {
                    LogoView(
                        logo = logo,
                        size = 32.dp
                    )
                }
                else {
                    Icon(
                        imageVector = Icons.Outlined.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(horizontal = 8.dp)
                    )
                }

                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth(0.82F),
                    style = MaterialTheme.typography.subtitle1
                )

                Text(
                    text = country,
                    style = MaterialTheme.typography.subtitle1
                )

                Icon(
                    imageVector = if(!expanded) Icons.Outlined.ArrowDropDown else Icons.Outlined.PlayArrow,
                    contentDescription = ""
                )
            }
        }

        AnimatedVisibility(
            visible = expanded,
        ) {
            content()
        }
    }
}