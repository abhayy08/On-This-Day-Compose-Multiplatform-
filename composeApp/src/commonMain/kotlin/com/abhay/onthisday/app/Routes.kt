package com.abhay.onthisday.app

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    data object MainGraph: Routes

    @Serializable
    data object HomeScreen: Routes

    @Serializable
    data class DetailsScreen(val identifierTitle: String): Routes

}