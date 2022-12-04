package com.codinlog.presentation.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * @description
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/12/4
 */

val BasePresentationDialog.scope: CoroutineScope
    get() = MainScope()

fun BasePresentationDialog.collectionOnVisible(block: suspend CoroutineScope.() -> Unit) {
    registerOnPresentationLifecycleCallback(object : IPresentationLifecycleCallback {
        override fun onVisible() {
            scope.launch {
                block()
            }
        }

        override fun onInvisible() {
            scope.cancel()
        }
    })
}