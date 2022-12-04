package com.codinlog.presentation.core

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * @description todo!
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/12/4
 */


val BasePresentationScreen.scope: CoroutineScope
    get() = MainScope()

fun BasePresentationScreen.collectionOnVisible(block: suspend CoroutineScope.() -> Unit) {
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
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
