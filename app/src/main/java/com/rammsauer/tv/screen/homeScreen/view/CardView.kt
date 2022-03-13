package com.rammsauer.tv.screen.homeScreen.view

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardView(
    logo    : ByteArray?,
    status  : Int,
    name    : String,
    country : String,
    onClick : (() -> Unit)
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        onClick = {
            onClick.invoke()
        }
    ) {
        Row(
            //horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if ((logo != null) && (BitmapFactory.decodeByteArray(logo, 0, logo.size) != null)
            ) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
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
                        imageVector = if (status == 1) Icons.Filled.CheckCircle else Icons.Outlined.Clear,
                        contentDescription = null
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .height(100.dp)
                        .padding(8.dp),
                ) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = country
                    )
                }

                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                )
            }
        }
    }
}