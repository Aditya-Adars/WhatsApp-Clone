package com.aditya.mywhatsappclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aditya.mywhatsappclone.presentation.navigation.WhatsAppNavigationSystem
import com.aditya.mywhatsappclone.ui.theme.MyWhatsAppCloneTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyWhatsAppCloneTheme {

                WhatsAppNavigationSystem()

                }
            }
        }
    }



