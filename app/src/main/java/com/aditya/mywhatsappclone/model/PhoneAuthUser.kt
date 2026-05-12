package com.aditya.mywhatsappclone.model

data class PhoneAuthUser(
    val uid: String="",
    val phoneNumber: String="",
    val name: String="",
    val status: String="",
    val image: String?=null
)
