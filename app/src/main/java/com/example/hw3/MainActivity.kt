package com.example.hw3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.widget.*
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var container1: LinearLayout
    private lateinit var addButton: Button
    private lateinit var container2: LinearLayout
    private lateinit var addButton2: Button
    private lateinit var participationInputText: EditText
    private lateinit var groupPresentationInputText: EditText
    private lateinit var finalProjectInputText: EditText
    private lateinit var calculate: Button
    private lateinit var reset: Button
    private lateinit var result: TextView

    private val MAX_CHILD_VIEWS = 5 // maximum number of child views

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container1 = findViewById(R.id.container1)
        addButton = findViewById(R.id.add_button)
        container2 = findViewById(R.id.container2)
        addButton2 = findViewById(R.id.add_button2)
        participationInputText = findViewById<EditText>(R.id.participation_grade)
        groupPresentationInputText = findViewById<EditText>(R.id.group_project_grade)
        finalProjectInputText = findViewById<EditText>(R.id.final_project_grade)
        calculate = findViewById(R.id.calculate)
        result = findViewById<TextView>(R.id.result_text_view)
        reset = findViewById(R.id.reset)


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
                container2.addView(editText)
                // Disable button if maximum limit has been reached
                if (container2.childCount == 2) {
                    addButton2.isEnabled = false
                }
            } else {
                Toast.makeText(this, "Maximum limit reached", Toast.LENGTH_SHORT).show()
            }
        }
        calculate.setOnClickListener {
            val hwGrades = mutableListOf<Double>()
            for (i in 1..5) {
                val hwEditText = container1.findViewWithTag<TextInputEditText>("Homework $i")
                if (!hwEditText?.text.isNullOrEmpty()) {
                    hwGrades.add(hwEditText.text.toString().toDouble())
                }
            }
            val midtermGrades = mutableListOf<Double>()
            for (i in 1..2) {
                val midtermEditText = container2.findViewWithTag<TextInputEditText>("Midterm $i")
                if (!midtermEditText?.text.isNullOrEmpty()) {
                    midtermGrades.add(midtermEditText.text.toString().toDouble())
                }
            }
            val participationGrade = participationInputText.text.toString().toDoubleOrNull() ?: 0.0
            val groupPresentationGrade = groupPresentationInputText.text.toString().toDoubleOrNull() ?: 0.0
            val finalProjectGrade = finalProjectInputText.text.toString().toDoubleOrNull() ?: 0.0

            val totalGrade = calculateTotal(participationGrade, hwGrades, groupPresentationGrade, midtermGrades, finalProjectGrade)
            result.text = String.format("%.2f", totalGrade)
        }
        reset.setOnClickListener {
            resetFields()
        }

    }
    private fun calculateTotal(
        participationGrade: Double,
        hwGrades: List<Double>,
        groupPresentationGrade: Double,
        midtermGrades: List<Double>,
        finalProjectGrade: Double
    ): Double {
        val hwTotal = if (hwGrades.isNotEmpty()) hwGrades.average() * 100 * 0.2 else 0.0
        val midtermTotal = if (midtermGrades.isNotEmpty()) midtermGrades.average() * 100 * 0.3 else 0.0
        val totalGrade = participationGrade * 0.1 + hwTotal + groupPresentationGrade * 0.1 + midtermTotal + finalProjectGrade * 0.3
        return totalGrade
    }
    private fun resetFields() {
        participationInputText.text.clear()
        groupPresentationInputText.text.clear()
        finalProjectInputText.text.clear()
        container1.removeAllViews()
        addButton.isEnabled = true
        container2.removeAllViews()
        addButton2.isEnabled = true
        result.text = ""
    }

}
