package com.example.hw3

import android.text.InputFilter
import android.text.Spanned

class InputFilterMinMax(private val min: String, private val max: String) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = (dest.toString() + source.toString()).toInt()
            if (isInRange(min, max, input)) {
                return null
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return ""
    }

    private fun isInRange(a: String, b: String, c: Int): Boolean {
        return if (b > a) c in (a.toInt() + 1)..(b.toInt() - 1) else c in (b.toInt() + 1)..(a.toInt() - 1)
    }
}
