package com.abhay.onthisday.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DetailsResponseDto(
    val query: Query
)

@Serializable
data class Query(
    val pages: Map<String, PageSummary>
)

@Serializable
data class PageSummary(
    val title: String,
    val extract: String? = null
)
