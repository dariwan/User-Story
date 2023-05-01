package com.example.storyapp.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.storyapp.R

class ButtonCostum: AppCompatButton {
    private lateinit var buttonBackground: Drawable
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = buttonBackground
    }
    private fun init() {
        buttonBackground = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable

    }
}