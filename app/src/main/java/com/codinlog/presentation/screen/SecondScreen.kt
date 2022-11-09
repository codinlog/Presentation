package com.codinlog.presentation.screen

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.codinlog.presentation.ApplicationViewModel
import com.codinlog.presentation.ApplicationViewModelFactory
import com.codinlog.presentation.PresentationScreenRoute
import com.codinlog.presentation.databinding.LayoutSecondScreenBinding

/**
 * @description TODO
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/9
 */

data class SecondScreenData(val text: String)

@SuppressLint("ViewConstructor")
class SecondScreen(context: Context, parent: BaseScreenContainer) :
    PresentationScreen(context, parent) {
    lateinit var mBinding: LayoutSecondScreenBinding
    lateinit var mViewModel: ApplicationViewModel

    override fun onCreateView(parent: BaseScreenContainer): View {
        mBinding = LayoutSecondScreenBinding.inflate(layoutInflater, parent, false)

        val viewModelStoreOwner =parent.findViewTreeViewModelStoreOwner()
            ?: throw IllegalStateException("viewModelStoreOwner is null")

        mViewModel = ViewModelProvider(viewModelStoreOwner, ApplicationViewModelFactory())[ApplicationViewModel::class.java]

        return mBinding.root
    }

    override fun onViewCreated() {
        super.onViewCreated()
        mBinding.btn.setOnClickListener {
            mViewModel.setPresentationScreenState(PresentationScreenRoute.FirstScreen)
        }
    }

}