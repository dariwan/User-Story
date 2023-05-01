package com.example.storyapp.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide.init
import com.example.storyapp.R


class EmailEditText: AppCompatEditText{

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
    }

    private fun init() {
        background = ContextCompat.getDrawable(context, R.drawable.et_background)

        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
                if (!text.isNullOrBlank()){
                    error = if (!checkEmailValid(text.toString())){
                        resources.getString(R.string.input_email_valid)
                    }else{
                        null
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (text.isNullOrEmpty()){
                    error = resources.getString(R.string.email_kosong)
                }
            }

        })
    }

    //fun buat cek bentuk email
    private fun checkEmailValid(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}