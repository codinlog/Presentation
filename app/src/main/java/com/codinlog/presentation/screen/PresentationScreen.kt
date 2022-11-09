package com.codinlog.presentation.screen

import android.content.Context
import android.view.View
import android.view.View.OnAttachStateChangeListener
import com.codinlog.presentation.core.IVisibleStateChangedListener
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
abstract class PresentationScreen(context: Context, parent: BaseScreenContainer) :
    BaseScreenContainer(context), IVisibleStateChangedListener {

    init {
        setContentView(onCreateView(parent))
        onViewCreated()
    }

    abstract fun onCreateView(parent: BaseScreenContainer): View

    open fun onViewCreated() {}

    override fun onVisible() {}

    override fun onInvisible() {}

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