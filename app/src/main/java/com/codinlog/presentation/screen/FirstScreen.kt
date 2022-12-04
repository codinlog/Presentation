package com.codinlog.presentation.screen

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.codinlog.presentation.ApplicationViewModel
import com.codinlog.presentation.ApplicationViewModelFactory
import com.codinlog.presentation.PresentationScreenRoute
import com.codinlog.presentation.databinding.LayoutFirstScreenBinding
import com.codinlog.presentation.core.BaseScreenContainer
import com.codinlog.presentation.core.BasePresentationScreen
import com.codinlog.presentation.screen.core.viewModelStoreOwner

/**
 * @description TODO
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/9
 */

data class FirstScreenData(val text: String)

private const val TAG = "FirstScreen"

@SuppressLint("ViewConstructor")
class FirstScreen(context: Context, parent: BaseScreenContainer) :
    BasePresentationScreen(context, parent) {
    lateinit var mBinding: LayoutFirstScreenBinding
    lateinit var mViewModel: ApplicationViewModel

    override fun onCreateView(parent: BaseScreenContainer): View {
        mBinding = LayoutFirstScreenBinding.inflate(layoutInflater, parent, false)

        mViewModel = ViewModelProvider(
            viewModelStoreOwner,
            ApplicationViewModelFactory()
        )[ApplicationViewModel::class.java]

        return mBinding.root
    }

    override fun onViewCreated() {
        super.onViewCreated()

        mBinding.btn.setOnClickListener {
            mViewModel.setPresentationScreenState(PresentationScreenRoute.SecondScreen)
        }

    }

}