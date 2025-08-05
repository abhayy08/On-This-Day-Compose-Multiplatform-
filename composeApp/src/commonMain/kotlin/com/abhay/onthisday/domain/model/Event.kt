package com.abhay.onthisday.domain.model

data class Event(
    val identifierTitle: String,
    val title: String,
    val description: String,
    val timeStamp: String,
    val extract: String,
    val thumbnail: String,
    val originalImage: String,
    val contentUrl: String,

)
