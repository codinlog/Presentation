package com.codinlog.presentation.screen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import androidx.core.animation.doOnEnd
import com.codinlog.presentation.databinding.LayoutAnimScreenBinding
import com.codinlog.presentation.core.BaseScreenContainer
import com.codinlog.presentation.core.BasePresentationScreen

/**
 * @description TODO
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/10
 */


class AnimScreen(context: Context, parent: BaseScreenContainer) :
    BasePresentationScreen(context, parent) {
    private lateinit var mBinding: LayoutAnimScreenBinding

    private lateinit var mAnimatorSet: AnimatorSet

    override fun onCreateView(parent: BaseScreenContainer): View {
        mBinding = LayoutAnimScreenBinding.inflate(layoutInflater, parent, false)

        mAnimatorSet = AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(mBinding.ivAnim, "translationY", -140f, -10f),
                ObjectAnimator.ofFloat(mBinding.ivAnim, "translationY", -10f, -140f)
            )
        }

        return mBinding.root
    }

    override fun onViewCreated() {
        super.onViewCreated()

        mAnimatorSet.doOnEnd {
            mAnimatorSet.start()
        }
    }

    override fun onVisible() {
        super.onVisible()

        mAnimatorSet.start()
    }

    override fun onInvisible() {
        super.onInvisible()

        mAnimatorSet.cancel()
    }

}