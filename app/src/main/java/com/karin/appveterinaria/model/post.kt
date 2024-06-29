package com.karin.appveterinaria.model

import com.google.firebase.Timestamp

data class Post(
    val Titulo: String = "",
    val content: String = "",
    val image: String = "",
    val timestamp: Timestamp? = null,
    val userID: String = "",
    val userName: String = ""
)