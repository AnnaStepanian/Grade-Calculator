package com.example.hw3

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast

class HomeworkManager(private val context: Context, private val homeworksGrade: LinearLayout, private val addHwButton: Button) {

    companion object {
        private const val MAX_CHILD_VIEWS = 5
    }

    fun addHomework() {
        if (homeworksGrade.childCount < MAX_CHILD_VIEWS) {
            val linearLayout = createLinearLayout()
            val editText = createEditText()
            val deleteButton = createDeleteButton(linearLayout)

            linearLayout.addView(editText)
            linearLayout.addView(deleteButton)
            homeworksGrade.addView(linearLayout)

            if (homeworksGrade.childCount == MAX_CHILD_VIEWS) {
                addHwButton.isEnabled = false
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

    private fun createEditText(): EditText {
        val editText = EditText(context)
        editText.hint = "Homework grade ${homeworksGrade.childCount + 1}"
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
            homeworksGrade.removeView(linearLayout)
            addHwButton.isEnabled = true
            updateEditTextHints()
        }
        return deleteButton
    }

    private fun updateEditTextHints() {
        for (i in 0 until homeworksGrade.childCount) {
            val child = homeworksGrade.getChildAt(i) as LinearLayout
            val childEditText = child.getChildAt(0) as EditText
            childEditText.hint = "Homework grade ${i + 1}"
        }
    }
}
