package com.codinlog.presentation.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ServiceLifecycleDispatcher
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import com.codinlog.presentation.ApplicationViewModel
import com.codinlog.presentation.ApplicationViewModelFactory
import com.codinlog.presentation.PresentationDialog
import com.codinlog.presentation.PresentationDialogState.ServiceHideState
import com.codinlog.presentation.PresentationDialogState.ServiceShowState
import com.codinlog.presentation.core.ApplicationViewModelStoreProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class PresentationService : Service(), LifecycleOwner, ViewModelStoreOwner {

    private val mDispatcher = ServiceLifecycleDispatcher(this)

    @Inject
    lateinit var mPresentationDialog: PresentationDialog

    private lateinit var mAppViewModel: ApplicationViewModel

    override fun onCreate() {
        mDispatcher.onServicePreSuperOnCreate()
        super.onCreate()

        mAppViewModel = ViewModelProvider(this, ApplicationViewModelFactory())[ApplicationViewModel::class.java]

        lifecycleScope.launchWhenCreated {
            mAppViewModel.presentationDialogStateFlow.collectLatest {
                when (it) {
                    ServiceShowState -> {
                        mPresentationDialog.show()
                    }

                    ServiceHideState -> {
                        mPresentationDialog.dismiss()
                    }
                }
            }

        }
    }

    override fun onStart(intent: Intent?, startId: Int) {
        mDispatcher.onServicePreSuperOnStart()
        super.onStart(intent, startId)
    }

    override fun onDestroy() {
        mDispatcher.onServicePreSuperOnDestroy()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun getLifecycle() = mDispatcher.lifecycle

    override fun getViewModelStore(): ViewModelStore = ApplicationViewModelStoreProvider
}