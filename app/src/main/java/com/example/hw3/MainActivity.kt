package com.example.hw3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var container1: LinearLayout
    private lateinit var addButton: Button
    private lateinit var container2: LinearLayout
    private lateinit var addButton2: Button

    private val MAX_CHILD_VIEWS = 5 // maximum number of child views

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container1 = findViewById(R.id.container1)
        addButton = findViewById(R.id.add_button)
        container2 = findViewById(R.id.container2)
        addButton2 = findViewById(R.id.add_button2)

        addButton.setOnClickListener {
            if (container1.childCount < MAX_CHILD_VIEWS) {
                val editText = EditText(this)
                editText.hint = "Homework grade ${container1.childCount + 1}"
                editText.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                editText.inputType = InputType.TYPE_CLASS_NUMBER
                editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(3),
                    InputFilterMinMax("0", "100"))
                container1.addView(editText)
                // Disable button if maximum limit has been reached
                if (container1.childCount == MAX_CHILD_VIEWS) {
                    addButton.isEnabled = false
                }
            } else {
                Toast.makeText(this, "Maximum limit reached", Toast.LENGTH_SHORT).show()
            }
        }

        addButton2.setOnClickListener {
            if (container2.childCount < 2) {
                val editText = EditText(this)
                editText.hint = "Midterm grade ${container2.childCount + 1}"
                editText.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                editText.inputType = InputType.TYPE_CLASS_NUMBER
                editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(3),
                    InputFilterMinMax("0", "100"))
                container2.addView(editText, 0)
                // Disable button if maximum limit has been reached
                if (container2.childCount == 2) {
                    addButton2.isEnabled = false
                }
            } else {
                Toast.makeText(this, "Maximum limit reached", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
