package com.codinlog.presentation

import android.app.Application
import android.widget.RemoteViews
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codinlog.presentation.PresentationDialogState.ServiceHideState
import com.codinlog.presentation.PresentationScreenRoute.FirstScreen
import com.codinlog.presentation.core.ApplicationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 使用与Application生命周期一致的ViewModel进行数据共享
 */

sealed interface PresentationScreenRoute {
    object FirstScreen : PresentationScreenRoute
    object SecondScreen : PresentationScreenRoute
    object AnimScreen : PresentationScreenRoute
    object CameraScreen: PresentationScreenRoute
    class RemoteScreen(val view: RemoteViews) : PresentationScreenRoute
}

sealed interface PresentationDialogState {
    object ServiceShowState : PresentationDialogState
    object ServiceHideState : PresentationDialogState
}

@HiltViewModel
class ApplicationViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    // 用于通知客屏显示状态
    private val mPresentationScreenRouteFlow =
        MutableSharedFlow<PresentationScreenRoute>(replay = 1)
    val presentationScreenFlow get() = mPresentationScreenRouteFlow

    init {
        MainScope().launch {
            mPresentationScreenRouteFlow.emit(FirstScreen)
        }
    }

    //第一屏数据
    private val mFirstScreenDataFlow = MutableSharedFlow<String>(replay = 1)
    val firstScreenDataFlow get() = mFirstScreenDataFlow

    // 用于通知服务端显示状态
    private val mPresentationDialogStateFlow =
        MutableSharedFlow<PresentationDialogState>(replay = 1)
    val presentationDialogStateFlow get() = mPresentationDialogStateFlow

    init {
        MainScope().launch {
            mPresentationDialogStateFlow.emit(ServiceHideState)
        }
    }

    fun setPresentationScreenState(state: PresentationScreenRoute) {
        MainScope().launch {
            mPresentationScreenRouteFlow.emit(state)
        }
    }

    fun setPresentationDialogState(state: PresentationDialogState) {
        MainScope().launch {
            mPresentationDialogStateFlow.emit(state)
        }
    }

}


class ApplicationViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Application::class.java)
            .newInstance(ApplicationProvider.application)
    }
}