package com.aditya.mywhatsappclone.presentation.callscreen

import android.annotation.SuppressLint
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showSystemUi = true)
fun CallScreen() {

    val sampleCall = listOf(
        Call(R.drawable.bhuvan_bam,"Bhuvan", "Yesterday 8:00 PM", true),
        Call(R.drawable.carryminati, name = "Carry", "Today 2:00 PM", false),
        Call(R.drawable.tripti_dimri, "Tripti", "Today 10:00 AM", false),
        Call(R.drawable.mrbeast, "Mr Beast","2 days ago 8:30 Pm", false),
        Call(R.drawable.boy, "Salman", "Today 3:00 AM", false),
        Call(R.drawable.kartik_aaryan, "Kartik", "Today 9:10 AM", true)
    )

    var isSearching by remember {
        mutableStateOf(false)
    }

    var search by remember {
        mutableStateOf("")
    }
    var showMenu by remember {
        mutableStateOf(false)
    }

    Scaffold(topBar = {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column {

                Row(modifier = Modifier.padding(top = 24.dp, start = 12.dp)) {
                    if (isSearching) {
                        TextField(
                            value = search, onValueChange = {
                                search = it
                            }, placeholder = {
                                Text(text = "Search")
                            }, colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            ), singleLine = true
                        )
                    } else {
                        Text(
                            text = "Calls",
                            fontSize = 28.sp,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if (isSearching) {
                        IconButton(onClick = {
                            isSearching = false
                            search = ""
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.cross),
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    } else {

                        IconButton(onClick = { isSearching = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.more),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = "Settings") },
                                    onClick = { showMenu = false })


                            }
                        }
                    }
                }
                HorizontalDivider()

            }
        }
    }, bottomBar = {
        BottomNavigation()

    },floatingActionButton = {
        FloatingActionButton(
            onClick = {},
            containerColor = colorResource(id = R.color.light_green),
            modifier = Modifier.size(65.dp),
            contentColor = Color.White
        ) {
            Icon(
                painter = painterResource(id = R.drawable.add_call),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    },

        ) {
        Column(modifier = Modifier.padding(it)) {
            Spacer(modifier = Modifier.height(16.dp))
            FavoriteSection()

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(colorResource(R.color.light_green)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Start a new call",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Recent Calls",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyColumn {
                items(sampleCall){ data->
                    CallItemDesign(data)
                }
            }
        }
    }
}