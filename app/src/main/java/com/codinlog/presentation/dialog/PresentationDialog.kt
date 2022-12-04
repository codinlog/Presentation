package com.codinlog.presentation.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Display
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.codinlog.presentation.ApplicationViewModel
import com.codinlog.presentation.ApplicationViewModelFactory
import com.codinlog.presentation.PresentationScreenRoute
import com.codinlog.presentation.core.ApplicationViewModelStoreProvider
import com.codinlog.presentation.core.BasePresentationDialog
import com.codinlog.presentation.core.collectionOnVisible
import com.codinlog.presentation.screen.AnimScreen
import com.codinlog.presentation.screen.CameraScreen
import com.codinlog.presentation.screen.FirstScreen
import com.codinlog.presentation.screen.SecondScreen
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * @description TODO
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/9
 */
private const val TAG = "PresentationDialog"

class PresentationDialog(context: Context, display: Display) :
    BasePresentationDialog(context, display),ViewModelStoreOwner {

    private lateinit var mAppViewModel: ApplicationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAppViewModel =
            ViewModelProvider(this, ApplicationViewModelFactory())[ApplicationViewModel::class.java]


        collectionOnVisible {
            mAppViewModel.presentationScreenFlow
                .distinctUntilChanged()
                .collect {
                showPresentationScreen(it)
            }
        }
    }

    private fun showPresentationScreen(state: PresentationScreenRoute) {
        Log.d(TAG, state.toString())
        when (state) {
            is PresentationScreenRoute.FirstScreen -> {
                setContentView(FirstScreen(context, container))
            }
            is PresentationScreenRoute.SecondScreen -> {
                setContentView(SecondScreen(context, container))
            }

            is PresentationScreenRoute.AnimScreen -> {
                setContentView(AnimScreen(context, container))
            }

            is PresentationScreenRoute.CameraScreen -> {
                setContentView(CameraScreen(context, container))
            }
        }
    }

    override fun getViewModelStore(): ViewModelStore = ApplicationViewModelStoreProvider

}