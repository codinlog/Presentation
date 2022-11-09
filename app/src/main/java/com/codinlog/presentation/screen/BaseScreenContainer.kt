package com.codinlog.presentation.screen

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnAttachStateChangeListener
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * @description 我们简单认为客屏显示view声明周期只有visible和invisible两种状态
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/9
 */
open class BaseScreenContainer(context: Context) : FrameLayout(context) {
    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }
}
