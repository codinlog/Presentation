package com.codinlog.presentation.screen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.codinlog.presentation.core.IVisibleStateChangedListener

/**
 * @description 我们简单认为客屏显示view声明周期只有visible和invisible两种状态
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/9
 */
abstract class BaseScreenContainer(context: Context) : FrameLayout(context),
    IVisibleStateChangedListener {
    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        setContentView(onCreateView())
        onViewCreated()
    }

    abstract fun onCreateView():View

    open fun onViewCreated() {}

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onVisible()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onInvisible()
    }

    private fun setContentView(view: View) {
        addView(view)
    }
}
