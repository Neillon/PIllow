package com.example.intro

import android.app.Application
import com.example.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieApplication)
            modules(
                listOf(presentationModule)
            )
        }
    }
}