package com.abhay.onthisday

import androidx.compose.ui.window.ComposeUIViewController
import com.abhay.onthisday.app.App
import com.abhay.onthisday.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}