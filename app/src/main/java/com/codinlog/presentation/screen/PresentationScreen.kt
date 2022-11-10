package com.codinlog.presentation.screen

import android.content.Context
import android.view.View
import android.view.View.OnAttachStateChangeListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * @description TODO
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/9
 */
abstract class PresentationScreen(context: Context, private val parent: BaseScreenContainer) :
    BaseScreenContainer(context) {

    init {
        val view = onCreateView(parent)
        setContentView(view)
        onViewCreated()
    }

    abstract fun onCreateView(parent: BaseScreenContainer): View

    open fun onViewCreated(){}

    private fun setContentView(view: View?) {
        if (view == null) return
        addView(view)
    }
}

val PresentationScreen.scope: CoroutineScope
    get() = MainScope()

fun PresentationScreen.collectionOnVisible(block: suspend CoroutineScope.() -> Unit) {
    addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {
            scope.launch {
                block()
            }
        }

        override fun onViewDetachedFromWindow(v: View) {
            scope.cancel()
        }
    })
}