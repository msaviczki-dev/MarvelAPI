package com.msaviczki.marvel_api.app

import android.app.Application
import com.msaviczki.marvel_api.di.initModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(initModule)
        }
    }
}