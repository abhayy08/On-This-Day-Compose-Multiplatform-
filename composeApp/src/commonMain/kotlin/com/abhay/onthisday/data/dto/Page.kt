package com.abhay.onthisday.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Page(
    val title: String,
    val tid: String,
    val normalizedtitle: String,
    val description: String? = null,
    val timestamp: String? = null,
    val thumbnail: Thumbnail? = null,
    val originalimage: OriginalImage? = null,
    val extract: String? = null
)