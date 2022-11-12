package com.codinlog.presentation.view

import android.content.Context
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import com.codinlog.presentation.R

class PresentationKeyboardView : KeyboardView {


    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    private val mAsciiKeyboard = Keyboard(context, R.xml.presentation_keyboard)


    init {
        keyboard = mAsciiKeyboard
    }

    fun switchToAsciiKeyboard() {
        keyboard = mAsciiKeyboard
    }
}