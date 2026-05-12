package com.aditya.mywhatsappclone.presentation.updatescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aditya.mywhatsappclone.R
import com.aditya.mywhatsappclone.presentation.bottom_navigation.BottomNavigation


@Composable
@Preview(showSystemUi = true)

fun UpdateScreen() {

    val scrollState = rememberScrollState()

    val sampleStatus = listOf(
        StatusData(image = R.drawable.bhuvan_bam, name = "Bhuvan", time = "10:00"),
        StatusData(image = R.drawable.boy1, name = "Shahrukh", time = "Just now"),
        StatusData(image = R.drawable.boy, name = "Salman", time = "2 hour ago")
    )

    val sampleChannels = listOf(
        Channels(image = R.drawable.neat_roots, name = "Neat Roots", description = "Latest news in tech"),
        Channels(image = R.drawable.img, "Food Lover", "Discover new chef"),
        Channels(R.drawable.meta,"Meta", "Explore World")

    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = colorResource(id = R.color.light_green),
                modifier = Modifier.size(65.dp),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        },

        bottomBar = {
            BottomNavigation()
        },
        topBar = {

            TopBar()
        }

    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            Text(
                text = "Status",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
            )
            MyStatus()

            sampleStatus.forEach {
                StatusItem(statusData = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                color = Color.Gray
            )

            Text(
                text = "Channels",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(12.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                Text(text = "stay updated on topics that matter to you. Find channels to follow below")
                
                Spacer(modifier = Modifier.height(28.dp))
                Text(text = "Find channels to follow")
            }
            Spacer(modifier = Modifier.height(16.dp))

            sampleChannels.forEach {
                ChannelItems(channels = it)
            }

        }
    }
}
