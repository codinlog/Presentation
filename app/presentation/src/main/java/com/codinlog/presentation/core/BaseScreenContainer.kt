package com.codinlog.presentation.core

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.codinlog.presentation.core.IPresentationLifecycleCallback

/**
 * @description 我们简单认为客屏显示view声明周期只有visible和invisible两种状态
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/9
 */
abstract class BaseScreenContainer(context: Context) : FrameLayout(context),
    IPresentationLifecycleCallback {
    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        visible()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        invisible()
    }

    internal fun visible() {
        onVisible()
        getChildAt(0)?.let {
            if (it is BaseScreenContainer) {
                it.visible()
            }
        }
    }

    internal fun invisible() {
        onInvisible()
        getChildAt(0)?.let {
            if (it is BaseScreenContainer) {
                it.visible()
            }
        }
    }

    override fun onVisible() {

    }

    override fun onInvisible() {

    }

    override fun addView(child: View?) {
        super.addView(child)
    }
}
