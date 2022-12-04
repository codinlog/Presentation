package com.codinlog.presentation

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.codinlog.presentation.databinding.ActivityKeyboardBinding
import com.google.common.util.concurrent.ListenableFuture

private const val TAG = "KeyboardActivity"
class KeyboardActivity : AppCompatActivity() ,KeyboardView.OnKeyboardActionListener{

    private lateinit var mBinding: ActivityKeyboardBinding

    private lateinit var mCameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityKeyboardBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.kvKeyboard.setOnKeyboardActionListener(this)


        mCameraProviderFuture = ProcessCameraProvider.getInstance(this)

        mCameraProviderFuture.addListener({
            val cameraProvider = mCameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder()
            .build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        preview.setSurfaceProvider(mBinding.previewView.surfaceProvider)

        val camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview)
    }

    override fun onPress(primaryCode: Int) {
    }

    override fun onRelease(primaryCode: Int) {
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
    }

    override fun onText(text: CharSequence?) {
        Log.d(TAG, "onText: $text")
    }

    override fun swipeLeft() {
    }

    override fun swipeRight() {
    }

    override fun swipeDown() {
    }

    override fun swipeUp() {
    }
}