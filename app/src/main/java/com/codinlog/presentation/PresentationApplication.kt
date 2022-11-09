package com.codinlog.presentation

import android.app.Application
import com.codinlog.presentation.core.ApplicationDelegate
import com.codinlog.presentation.core.ApplicationViewModelStoreDelegate
import com.codinlog.presentation.core.IApplicationDelegate
import com.codinlog.presentation.core.IApplicationViewModelStoreDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PresentationApplication : Application(), IApplicationDelegate by ApplicationDelegate(),
    IApplicationViewModelStoreDelegate by ApplicationViewModelStoreDelegate() {
    override fun onCreate() {
        super.onCreate()

        setupApplication()
        setupApplicationViewModelStore()
    }
}