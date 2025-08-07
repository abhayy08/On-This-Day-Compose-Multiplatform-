package com.abhay.onthisday

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.abhay.onthisday.app.App
import com.abhay.onthisday.di.initKoin

fun main() {

    initKoin()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "On This Day",
        ) {
            App(isDesktop = isDesktopPlatform())
        }
    }
}

fun isDesktopPlatform(): Boolean {
    return System.getProperty("os.name")?.let {
        it.contains("Windows") || it.contains("Linux") || it.contains("Mac")
    } ?: false
}