package com.rammsauer.tv.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen(
    isLoading : Boolean,
    content   : @Composable () -> Unit
) = if (isLoading) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )

        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ConnectedTv,
                        contentDescription = "",
                        modifier = Modifier
                            .size(64.dp)
                    )

                    Text(
                        text = "Aktualisieren der Senderliste.\n",
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(
                        modifier = Modifier
                            .size(64.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Outlined.DownloadForOffline,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                )
            }
        }
    }
} else {
    content()
}