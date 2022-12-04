package com.codinlog.presentation.screen.core

import android.content.Context
import android.inputmethodservice.KeyboardView
import android.text.InputType
import android.util.Log
import android.view.Gravity.BOTTOM
import android.view.Gravity.CENTER_HORIZONTAL
import android.view.MotionEvent
import android.widget.EditText
import androidx.annotation.UiThread
import com.codinlog.presentation.databinding.LayoutPresentationKeyboardBinding

/**
 * @description todo!
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/12
 */

private const val TAG = "PresentationKeyboardScreen"

abstract class PresentationKeyboardScreen(context: Context, parent: BaseScreenContainer) :
    PresentationScreen(context, parent), KeyboardView.OnKeyboardActionListener {

    private lateinit var mKeyboardBinding: LayoutPresentationKeyboardBinding

    private var mAreKeyboardDisplayed = false

    private var mEditText: EditText? = null

    var cancelTouchedOutsideKeyboard = true

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Log.d(TAG, "dispatchTouchEvent Event")
        if (ev.actionMasked == MotionEvent.ACTION_DOWN && mAreKeyboardDisplayed) {
            val outside = areTouchedKeyboardOutside(ev)

            Log.d(TAG, "on Touch Event: outside = $outside")

            if (outside) {
                if (cancelTouchedOutsideKeyboard) hideKeyboard()
                return true
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    @UiThread
    fun showKeyboard(editText: EditText) {
        editText.inputType = InputType.TYPE_NULL
        mEditText = editText

        if (mAreKeyboardDisplayed) return
        mAreKeyboardDisplayed = true

        maybeCreateKeyboardView()

        addView(mKeyboardBinding.root)
    }

    @UiThread
    fun hideKeyboard() {
        if (!mAreKeyboardDisplayed) return
        mAreKeyboardDisplayed = false

        removeView(mKeyboardBinding.root)

        mEditText = null
    }

    override fun onText(text: CharSequence?) {
        mEditText?.text?.append(text)
    }

    override fun onPress(primaryCode: Int) {

    }

    override fun onRelease(primaryCode: Int) {

    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {

    }

    override fun swipeLeft() {

    }

    override fun swipeRight() {

    }

    override fun swipeDown() {

    }

    override fun swipeUp() {

    }

    private fun maybeCreateKeyboardView() {
        if (::mKeyboardBinding.isInitialized) return
        mKeyboardBinding =
            LayoutPresentationKeyboardBinding.inflate(layoutInflater, parent, false)

        mKeyboardBinding.root.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = BOTTOM or CENTER_HORIZONTAL
        }

        mKeyboardBinding.kvKeyboard.setOnKeyboardActionListener(this)
    }

    /**
     * 判断点击区域是否在键盘区域内
     */
    private fun areTouchedKeyboardOutside(event: MotionEvent): Boolean {
        val keyboardView = mKeyboardBinding.root
        val location = IntArray(2)
        keyboardView.getLocationOnScreen(location)
        val x = event.rawX
        val y = event.rawY
        return x < location[0] || x > location[0] + keyboardView.width || y < location[1] || y > location[1] + keyboardView.height

    }

}