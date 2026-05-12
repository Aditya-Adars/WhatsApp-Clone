package com.aditya.mywhatsappclone.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

     @Serializable
     data object SplashScreen: Routes()

    @Serializable
    data object WelcomeScreen: Routes()

    @Serializable
    data object UserRegistration: Routes()

    @Serializable
    data object HomeScreen: Routes()

    @Serializable
    data object UpdateScreen: Routes()

    @Serializable
    data object CommunityScreen: Routes()

    @Serializable
    data object CallScreen: Routes()

    @Serializable
    data object UserProfileSetScreen: Routes()
}