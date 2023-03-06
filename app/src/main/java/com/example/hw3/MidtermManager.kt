package com.example.hw3

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast

class MidtermManager(
    private val context: Context,
    private val midtermsLayout: LinearLayout,
    private val addMidtermsButton: Button
) {

    companion object {
        private const val MAX_MIDTERMS = 2
    }

    fun addMidtermGrade() {
        if (midtermsLayout.childCount < MAX_MIDTERMS) {
            val linearLayout = createLinearLayout()
            val editText = createEditText(midtermsLayout.childCount + 1)
            val deleteButton = createDeleteButton(linearLayout)

            linearLayout.addView(editText)
            linearLayout.addView(deleteButton)

            midtermsLayout.addView(linearLayout)

            if (midtermsLayout.childCount == MAX_MIDTERMS) {
                addMidtermsButton.isEnabled = false
            }
        } else {
            Toast.makeText(context, "Maximum limit reached", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createLinearLayout(): LinearLayout {
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        return linearLayout
    }

    private fun createEditText(index: Int): EditText {
        val editText = EditText(context)
        editText.hint = "Midterm grade $index"
        editText.layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.filters = arrayOf<InputFilter>(
            InputFilter.LengthFilter(3),
            InputFilterMinMax("0", "100"),
            ZeroInputFilter()
        )
        return editText
    }

    private fun createDeleteButton(linearLayout: LinearLayout): Button {
        val deleteButton = Button(context)
        deleteButton.text = "Delete"
        deleteButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        deleteButton.setOnClickListener {
            midtermsLayout.removeView(linearLayout)
            addMidtermsButton.isEnabled = true
            updateEditTextHints()
        }
        return deleteButton
    }

    private fun updateEditTextHints() {
        for (i in 0 until midtermsLayout.childCount) {
            val child = midtermsLayout.getChildAt(i) as LinearLayout
            val childEditText = child.getChildAt(0) as EditText
            childEditText.hint = "Midterm grade ${i + 1}"
        }
    }
}
