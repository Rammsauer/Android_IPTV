package com.rammsauer.tv.Screen.HomeScreen.View

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardView(
    logo    : ByteArray?,
    status  : Int,
    name    : String,
    onClick : (() -> Unit)
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            //horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if ((logo != null) && (BitmapFactory.decodeByteArray(logo, 0, logo.size) != null)
            ) {
                Box(
                    modifier = Modifier.padding(4.dp)
                ) {
                    LogoView(
                        logo = logo,
                        size = 100.dp
                    )
                    Icon(
                        imageVector = if (status == 1) Icons.Filled.CheckCircle else Icons.Outlined.Clear,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
            } else {
                Box {
                    Icon(
                        imageVector = Icons.Outlined.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(horizontal = 8.dp)
                    )

                    Icon(
                        imageVector = if (status.equals(1)) Icons.Filled.CheckCircle else Icons.Outlined.Clear,
                        contentDescription = null
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                ) {
                    Text(
                        text = "$name",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Derzeitig keine Programmvorschau verfügbar"
                    )
                }

                Button(
                    onClick = {
                        onClick.invoke()
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                        )
                    },
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}