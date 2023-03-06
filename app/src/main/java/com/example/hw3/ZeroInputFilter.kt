package com.example.hw3

import android.text.InputFilter
import android.text.Spanned

class ZeroInputFilter : InputFilter {

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (dest.toString() == "0" && source.toString() != "") {
            return ""
        }
        return null
    }
}
