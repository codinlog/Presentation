package com.codinlog.presentation.screen

import android.content.Context
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.codinlog.presentation.core.BasePresentationScreen
import com.codinlog.presentation.core.BaseScreenContainer
import com.codinlog.presentation.databinding.LayoutCameraScreenBinding

/**
 * @description TODO
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/14
 */
class CameraScreen(context: Context, parent: BaseScreenContainer) :
    BasePresentationScreen(context, parent), LifecycleOwner {

    private val mLifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    private lateinit var mCameraProviderFuture: com.google.common.util.concurrent.ListenableFuture<ProcessCameraProvider>

    init {
        mLifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    private lateinit var mBinding: LayoutCameraScreenBinding

    override fun onCreateView(parent: BaseScreenContainer): View {
        mBinding = LayoutCameraScreenBinding.inflate(layoutInflater, parent, false)

        mCameraProviderFuture = ProcessCameraProvider.getInstance(context)

        mCameraProviderFuture.addListener({
            val cameraProvider = mCameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(context))

        return mBinding.root
    }

    override fun onViewCreated() {
        super.onViewCreated()

    }

    override fun onVisible() {
        super.onVisible()
        mLifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    override fun onInvisible() {
        super.onInvisible()
        mLifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    override fun getLifecycle(): Lifecycle = mLifecycleRegistry

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder()
            .build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        preview.setSurfaceProvider(mBinding.previewView.surfaceProvider)

        val camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview)
    }

}