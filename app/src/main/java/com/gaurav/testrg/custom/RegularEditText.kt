package com.gaurav.testrg.custom

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class RegularEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {
    init {
        val typeface = Typeface.createFromAsset(getContext().assets, "font/kameron_regular.ttf")
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB ||
            android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
            setTypeface(typeface)
        }
    }
}