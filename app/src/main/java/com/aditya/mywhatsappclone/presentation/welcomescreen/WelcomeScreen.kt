package com.aditya.mywhatsappclone.presentation.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.aditya.mywhatsappclone.R
import com.aditya.mywhatsappclone.presentation.navigation.Routes

@Composable
fun WelcomeScreen(navHostController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.whatsapp_sticker),
            null,
            modifier = Modifier.size(300.dp)
        )
        Text(text = "Welcome Whatsapp", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Text(text = "Read our", color = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Privacy Policy", color = colorResource(id = R.color.light_green))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Tap 'Agree to continue' to", color = Color.Gray)
        }

        Row {
            Text(text = "accept the", color = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Terms of Services", color = colorResource(id = R.color.light_green))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {navHostController.navigate(Routes.UserRegistration)},
            modifier = Modifier.size(280.dp, 43.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.dark_green)
            )
        ) {
            Text(text = "Agree and Continue", fontSize = 16.sp)
        }
    }
}