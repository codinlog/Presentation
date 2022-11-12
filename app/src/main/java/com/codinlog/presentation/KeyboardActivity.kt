package com.codinlog.presentation

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.codinlog.presentation.databinding.ActivityKeyboardBinding

private const val TAG = "KeyboardActivity"
class KeyboardActivity : AppCompatActivity() ,KeyboardView.OnKeyboardActionListener{

    private lateinit var mBinding: ActivityKeyboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityKeyboardBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.kvKeyboard.setOnKeyboardActionListener(this)
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