package com.abhay.onthisday

import android.app.Application
import com.abhay.onthisday.di.initKoin
import org.koin.android.ext.koin.androidContext

class OnThisDayApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@OnThisDayApp)
        }
    }

}