package com.aditya.mywhatsappclone.presentation.callscreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aditya.mywhatsappclone.R


@Composable
@Preview(showSystemUi = true)
fun FavoriteSection() {

    val sampleContacts = listOf(
        FavoriteContact(image = R.drawable.mrbeast, "Mr Beast"),
        FavoriteContact(R.drawable.bhuvan_bam, "Bhuvan"),
        FavoriteContact(R.drawable.tripti_dimri, "Tripti"),
        FavoriteContact(R.drawable.carryminati, "Carry"),
        FavoriteContact(R.drawable.boy, "Salman"),
        FavoriteContact(R.drawable.boy1, "Shahrukh"),


    )

    Column(modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 16.dp)) {
        Text(
            text = "Favorites",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())) {

            sampleContacts.forEach {
                FavoriteItem(it)
            }
        }
    }
}

data class FavoriteContact(val image: Int, val name: String)