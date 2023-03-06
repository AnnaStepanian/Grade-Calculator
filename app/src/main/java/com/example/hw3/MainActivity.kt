package com.example.hw3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.widget.*
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private var homeworkManager: HomeworkManager? = null
    private var midtermManager: MidtermManager? = null
    private var homeworksGrade: LinearLayout? = null
    private var addHwButton: Button? = null
    private var midterms: LinearLayout? = null
    private var addMidtermsButton: Button? = null
    private var participationGrade: EditText? = null
    private var groupPresentationGrade: EditText? = null
    private var finalProjectGrade: EditText? = null
    private var calculateButton: Button? = null
    private var resetButton: Button? = null
    private var overallGrade: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeworksGrade = findViewById(R.id.hw_grade)
        addHwButton = findViewById(R.id.addHwButton)
        midterms = findViewById(R.id.midterms_grade)
        addMidtermsButton = findViewById(R.id.addMidtermButton)
        participationGrade = findViewById<EditText>(R.id.participation_grade)
        groupPresentationGrade = findViewById<EditText>(R.id.group_project_grade)
        finalProjectGrade = findViewById<EditText>(R.id.final_project_grade)
        calculateButton = findViewById(R.id.calculate)
        overallGrade = findViewById<TextView>(R.id.result_text_view)
        resetButton = findViewById(R.id.reset)
        homeworkManager = HomeworkManager(this, homeworksGrade!!, addHwButton!!)
        midtermManager = MidtermManager(this, midterms!!, addMidtermsButton!!)

        participationGrade!!.filters = arrayOf<InputFilter>(
            InputFilterMinMax("0", "100"),
            ZeroInputFilter()
        )

        groupPresentationGrade!!.filters = arrayOf<InputFilter>(
            InputFilterMinMax("0", "100"),
            ZeroInputFilter()
        )

        finalProjectGrade!!.filters = arrayOf<InputFilter>(
            InputFilterMinMax("0", "100"),
            ZeroInputFilter()
        )



        addHwButton!!.setOnClickListener {
            homeworkManager!!.addHomework()
        }


        addMidtermsButton!!.setOnClickListener {
            midtermManager!!.addMidtermGrade()
        }


        calculateButton!!.setOnClickListener {
            calculateTotalGrade()
        }

        resetButton!!.setOnClickListener {
            resetFields()
        }

    }
    fun calculateTotalGrade() {
        val hwGrades = mutableListOf<Double>()
        for (i in 1..5) {
            val hwEditText = homeworksGrade?.findViewWithTag<TextInputEditText>("Homework $i")
            if (!hwEditText?.text.isNullOrEmpty()) {
                hwGrades.add(hwEditText!!.text.toString().toDouble())
            }
        }
        val midtermGrades = mutableListOf<Double>()
        for (i in 1..2) {
            val midtermEditText = midterms?.findViewWithTag<TextInputEditText>("Midterm $i")
            if (!midtermEditText?.text.isNullOrEmpty()) {
                midtermGrades.add(midtermEditText!!.text.toString().toDouble())
            }
        }
        val participationGrade = participationGrade?.text.toString().toDoubleOrNull() ?: 0.0
        val groupPresentationGrade = groupPresentationGrade?.text.toString().toDoubleOrNull() ?: 0.0
        val finalProjectGrade = finalProjectGrade?.text.toString().toDoubleOrNull() ?: 0.0

        val totalGrade = calculateTotal(participationGrade, hwGrades, groupPresentationGrade, midtermGrades, finalProjectGrade)
        overallGrade?.text = String.format("%.2f", totalGrade)
    }


    private fun calculateTotal(
        participationGrade: Double,
        hwGrades: List<Double>,
        groupPresentationGrade: Double,
        midtermGrades: List<Double>,
        finalProjectGrade: Double
    ): Double {
        val hwTotal = if (hwGrades.isNotEmpty()) hwGrades.average() * 100 * 0.2 else 100.0
        val firstMidtermGrade = if (midtermGrades.size > 0) midtermGrades[0] else 100.0
        val secondMidtermGrade = if (midtermGrades.size > 1) midtermGrades[1] else 100.0
        val midtermTotal = firstMidtermGrade * 0.1 + secondMidtermGrade * 0.2
        val totalGrade = participationGrade * 0.1 + hwTotal + groupPresentationGrade * 0.1 + midtermTotal + finalProjectGrade * 0.3
        return totalGrade
    }

    private fun resetFields() {
        participationGrade?.text?.clear()
        groupPresentationGrade?.text?.clear()
        finalProjectGrade?.text?.clear()
        homeworksGrade?.removeAllViews()
        addHwButton?.isEnabled = true
        midterms?.removeAllViews()
        addMidtermsButton?.isEnabled = true
        overallGrade?.text = ""
    }


}
