package com.aditya.mywhatsappclone.presentation.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.aditya.mywhatsappclone.presentation.chatbox.ChatDesign
import com.aditya.mywhatsappclone.presentation.chatbox.ChatDesignModel


@Composable
@Preview(showSystemUi = true)
fun HomeScreen(){
    val chatData = listOf(
        ChatDesignModel(
            R.drawable.salmankhan,
            name = "Salman Khan",
            time = "10:00 AM",
            message = "Hi"
        ),
        ChatDesignModel(
            R.drawable.rashmika,
            name = "Rashmika",
            time = "12:12 AM",
            message = "Kaha ho?"
        ),
        ChatDesignModel(
            R.drawable.sharukhkhan,
            name = "Shahrokh",
            time = "11:11 AM",
            message = "Haa Bhai"
        ),
        ChatDesignModel(
            R.drawable.bhuvan_bam,
            name = "Bhuvan Bam",
            time = "10:10 AM",
            message = "Nahi re"
        ),
        ChatDesignModel(
            R.drawable.carryminati,
            name = "Carry Bhai",
            time = "5:55 AM",
            message = "mera hai"
        ),




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
                    painter = painterResource(id = R.drawable.add_chat_icon),
                    null,
                    modifier = Modifier.size(28.dp),
                )
            }
        },
        bottomBar = {
            BottomNavigation()
        }
    ) {
        Column(modifier = Modifier.padding(it)) {

            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "WhatsApp", fontSize = 28.sp,
                    color = colorResource(id = R.color.light_green),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp),
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                ) {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),

                            )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.more),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),

                            )
                    }
                }
            }
            HorizontalDivider()

            LazyColumn {
                items(chatData){
                    ChatDesign(chatDesignModel = it)
                }
            }
        }
    }
}