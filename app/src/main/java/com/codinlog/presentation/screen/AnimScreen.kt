package com.codinlog.presentation.screen

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteView
import androidx.core.animation.doOnEnd
import com.codinlog.presentation.R

/**
 * @description TODO
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/10
 */
typealias OnViewUpdateListener = (view: RemoteViews) -> Unit

@RemoteView
class AnimScreen(context: Context) : RemoteViews(context.packageName, R.layout.layout_anim_screen) {
    private var mOnViewUpdateListener: OnViewUpdateListener? = null

    private val mAnimSet = AnimatorSet().apply {
        val nearAnim = ValueAnimator.ofFloat(-140F, -10F).apply {
            duration = 1000
            addUpdateListener {
                val value = it.animatedValue as Float
                setFloat(R.id.tap_card, "setTranslationY", value)

                mOnViewUpdateListener?.invoke(this@AnimScreen)
            }
        }

        val leaveAnim = nearAnim.clone().apply {
            reverse()
            addUpdateListener {
                val value = it.animatedValue as Float
                setFloat(R.id.tap_card, "setTranslationY", value)
                mOnViewUpdateListener?.invoke(this@AnimScreen)
            }
        }

        playSequentially(nearAnim, leaveAnim)
    }

    fun setOnViewUpdateListener(onViewUpdateListener: OnViewUpdateListener) {
        mOnViewUpdateListener = onViewUpdateListener
    }

    fun startAnim() {
        mAnimSet.start()

        mAnimSet.doOnEnd {
            mAnimSet.start()
        }
    }

    fun stopAnim() {
        mAnimSet.cancel()
    }

}