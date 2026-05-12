package com.aditya.mywhatsappclone.model

data class Message(
    val senderPhoneNumber: String = "",
    val receiverPhoneNumber: String = "",
    val message: String = "",
    val timestamp: Long = 0L
)
