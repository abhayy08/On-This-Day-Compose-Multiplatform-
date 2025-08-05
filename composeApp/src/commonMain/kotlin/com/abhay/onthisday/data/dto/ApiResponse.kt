package com.abhay.onthisday.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseDto(
    val events: List<Event>
)
