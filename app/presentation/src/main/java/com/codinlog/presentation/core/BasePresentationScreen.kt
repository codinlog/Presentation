package com.codinlog.presentation.core

import android.content.Context
import android.view.View

/**
 * @description TODO
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/9
 */
abstract class BasePresentationScreen(context: Context, val parent: BaseScreenContainer) :
    BaseScreenContainer(context) {

    init {
        val view = onCreateView(parent)
        setContentView(view)
        onViewCreated()
    }

    abstract fun onCreateView(parent: BaseScreenContainer): View

    open fun onViewCreated() {}

    private fun setContentView(view: View?) {
        addView(view)
    }
}