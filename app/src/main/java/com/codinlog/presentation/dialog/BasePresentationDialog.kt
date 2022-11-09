package com.codinlog.presentation.dialog

import android.app.Presentation
import android.content.Context
import android.content.DialogInterface.OnDismissListener
import android.content.DialogInterface.OnShowListener
import android.os.Bundle
import android.view.Display
import android.view.View
import android.widget.RemoteViews
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.ViewTreeViewModelStoreOwner
import com.codinlog.presentation.core.IVisibleStateChangedListener
import com.codinlog.presentation.core.ApplicationViewModelStoreProvider
import com.codinlog.presentation.screen.BaseScreenContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * @param context 可以使用application、activity、service
 * 如果是application、service，需要在manifest中注册权限 android.permission.SYSTEM_ALERT_WINDOW
 */

open class BasePresentationDialog(context: Context, display: Display) :
    Presentation(context, display), IVisibleStateChangedListener, ViewModelStoreOwner {

    private var mOnShowListener: OnShowListener? = null
    private var mOnDismissListener: OnDismissListener? = null
    private lateinit var mBaseContainer: BaseScreenContainer

    val container: BaseScreenContainer
        get() = mBaseContainer

    private val mOnVisibleStateChangedListener = mutableListOf<IVisibleStateChangedListener>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.setOnShowListener {
            mOnShowListener?.onShow(this)
            onVisible()
        }

        super.setOnDismissListener {
            mOnDismissListener?.onDismiss(this)
            onInvisible()
        }

        mBaseContainer = BaseScreenContainer(context).apply {
            ViewTreeViewModelStoreOwner.set(this, this@BasePresentationDialog)
        }
        super.setContentView(mBaseContainer)
    }

    override fun setContentView(view: View) {
        mBaseContainer.removeAllViews()
        mBaseContainer.addView(view)
    }

    fun setRemoteViews(view: RemoteViews) {
        setContentView(view.apply(context, mBaseContainer))
    }

    override fun setOnShowListener(listener: OnShowListener?) {
        mOnShowListener = listener
    }

    override fun setOnDismissListener(listener: OnDismissListener?) {
        mOnDismissListener = listener
    }

    fun addOnVisibleStateChangeListener(listener: IVisibleStateChangedListener) {
        mOnVisibleStateChangedListener.add(listener)
    }

    override fun onVisible() {
        mOnVisibleStateChangedListener.forEach {
            it.onVisible()
        }
    }

    override fun onInvisible() {
        mOnVisibleStateChangedListener.forEach {
            it.onInvisible()
        }
    }

    override fun getViewModelStore(): ViewModelStore = ApplicationViewModelStoreProvider
}

val BasePresentationDialog.scope: CoroutineScope
    get() = MainScope()

fun BasePresentationDialog.collectionOnVisible(block: suspend CoroutineScope.() -> Unit) {
    addOnVisibleStateChangeListener(object : IVisibleStateChangedListener {
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