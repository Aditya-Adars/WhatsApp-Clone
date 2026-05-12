package com.aditya.mywhatsappclone.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aditya.mywhatsappclone.presentation.callscreen.CallScreen
import com.aditya.mywhatsappclone.presentation.communityscreen.CommunityScreen
import com.aditya.mywhatsappclone.presentation.homescreen.HomeScreen
import com.aditya.mywhatsappclone.presentation.profile.UserProfileSetScreen
import com.aditya.mywhatsappclone.presentation.splashscreen.SplashScreen
import com.aditya.mywhatsappclone.presentation.updatescreen.UpdateScreen
import com.aditya.mywhatsappclone.presentation.userregistration.UserRegisterScreen
import com.aditya.mywhatsappclone.presentation.welcomescreen.WelcomeScreen


@Composable
fun WhatsAppNavigationSystem(){

     val navController = rememberNavController()

    NavHost(startDestination = Routes.SplashScreen, navController = navController) {

        composable<Routes.SplashScreen> {
            SplashScreen(navController)
        }

        composable<Routes.WelcomeScreen> {
            WelcomeScreen(navController)
        }

        composable<Routes.UserRegistration> {
            UserRegisterScreen(navController)
        }

        composable<Routes.HomeScreen> {
            HomeScreen()
        }

        composable<Routes.UpdateScreen> {
            UpdateScreen()
        }

        composable<Routes.CommunityScreen> {
            CommunityScreen()
        }

        composable<Routes.CallScreen> {
            CallScreen()
        }

        composable<Routes.UserProfileSetScreen> {
            UserProfileSetScreen(navHostController = navController)
        }
    }

}