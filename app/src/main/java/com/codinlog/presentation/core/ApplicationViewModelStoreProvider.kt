package com.codinlog.presentation.core

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore

/**
 * @description TODO
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/9
 */

interface IApplicationViewModelStoreDelegate {
    val applicationViewModelStoreProvider: ApplicationViewModelStoreProvider
    fun Application.setupApplicationViewModelStore()
}

class ApplicationViewModelStoreDelegate : IApplicationViewModelStoreDelegate {
    override val applicationViewModelStoreProvider = ApplicationViewModelStoreProvider

    override fun Application.setupApplicationViewModelStore() {
        applicationViewModelStoreProvider.setup(this)
    }
}


object ApplicationViewModelStoreProvider:ViewModelStore() {
    fun setup(application: Application){}
}