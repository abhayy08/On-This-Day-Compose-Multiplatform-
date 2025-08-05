package com.abhay.onthisday.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val text: String,
    val pages: List<Page>,
    val year: Int
)