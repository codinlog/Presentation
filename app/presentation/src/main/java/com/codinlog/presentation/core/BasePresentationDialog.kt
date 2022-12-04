package com.codinlog.presentation.core

import android.app.Presentation
import android.content.Context
import android.content.DialogInterface.OnDismissListener
import android.content.DialogInterface.OnShowListener
import android.os.Bundle
import android.view.Display
import android.view.View

/**
 * @param context 可以使用application、activity、service
 * 如果是application、service，需要在manifest中注册权限 android.permission.SYSTEM_ALERT_WINDOW
 */

private const val TAG = "BasePresentationDialog"

abstract class BasePresentationDialog(context: Context, display: Display) :
    Presentation(context, display), IPresentationLifecycleCallback {

    private var mOnShowListener: OnShowListener? = null
    private var mOnDismissListener: OnDismissListener? = null
    private val mBaseContainer: BaseScreenContainer = object : BaseScreenContainer(context) {}

    val container: BaseScreenContainer
        get() = mBaseContainer

    private val mPresentationLifecycleCallbacks = mutableListOf<IPresentationLifecycleCallback>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.setOnShowListener {
            visible()
        }

        super.setOnDismissListener {
            invisible()
        }
        super.setContentView(mBaseContainer)
    }

    override fun setContentView(view: View) {
        mBaseContainer.removeAllViews()
        mBaseContainer.addView(view)
    }

    override fun setOnShowListener(listener: OnShowListener?) {
        mOnShowListener = listener
    }

    override fun setOnDismissListener(listener: OnDismissListener?) {
        mOnDismissListener = listener
    }

    fun registerOnPresentationLifecycleCallback(callback: IPresentationLifecycleCallback) {
        mPresentationLifecycleCallbacks.add(callback)
    }

    private fun visible() {
        mBaseContainer.visible()
        mOnShowListener?.onShow(this)
        onVisible()
    }

    private fun invisible() {
        mBaseContainer.invisible()
        mOnDismissListener?.onDismiss(this)
        onInvisible()
    }

    final override fun onVisible() {
        mPresentationLifecycleCallbacks.forEach {
            it.onVisible()
        }
    }

    final override fun onInvisible() {
        mPresentationLifecycleCallbacks.forEach {
            it.onInvisible()
        }
    }
}
