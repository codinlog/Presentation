package com.codinlog.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PresentationApplication:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}