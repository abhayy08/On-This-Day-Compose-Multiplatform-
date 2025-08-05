package com.abhay.onthisday

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform