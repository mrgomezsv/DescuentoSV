package com.descuentosv.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    companion object {
        const val AFP_PERCENTAGE_15 = 0.05
        const val AFP_PERCENTAGE_30 = 0.0725
        const val ISSS_PERCENTAGE_15 = 0.02
        const val ISSS_PERCENTAGE_30 = 0.03

        val RENTA_TABLE = listOf(
            RentaRange(0.01, 472.00, 0.0, 0.0, 0.0),
            RentaRange(472.01, 895.24, 0.10, 17.67, 472.00),
            RentaRange(895.25, 2038.10, 0.20, 60.00, 895.24),
            RentaRange(2038.11, Double.MAX_VALUE, 0.30, 288.57, 2038.10)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun calculateDeductions(view: View) {
        val editTextSalary = findViewById<EditText>(R.id.editTextSalary)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)
        val textViewCash = findViewById<TextView>(R.id.textViewCash)
        val spinnerDays = findViewById<Spinner>(R.id.spinnerDays)

        val salary = editTextSalary.text.toString().toDoubleOrNull()

        if (salary != null) {
            val selectedDaysText = spinnerDays.selectedItem.toString()
            val selectedDays = when {
                selectedDaysText.contains("15") -> 15
                selectedDaysText.contains("30") -> 30
                else -> 0
            }

            val afpPercentage = if (selectedDays == 15) AFP_PERCENTAGE_15 else AFP_PERCENTAGE_30
            val isssPercentage = if (selectedDays == 15) ISSS_PERCENTAGE_15 else ISSS_PERCENTAGE_30

            val afp = salary * afpPercentage
            val isss = salary * isssPercentage
            val totalDeductions = afp + isss

            val renta = calculateRenta(salary, RENTA_TABLE)

            val netSalary = (salary / 30) * selectedDays - totalDeductions - renta

            val resultRenta = if (renta > 0) "Renta: $$renta" else "No aplica descuento de Renta"
            val resultText = "Deducciones y prestaciones:\nAFP: $$afp\nISSS: $$isss"
            val resultCash = "Total en Dolares a Recibir: $$netSalary"

            textViewResult.text = resultText
            textViewCash.text = "$resultRenta\n$resultCash"
        } else {
            textViewResult.text = "Ingrese un monto válido"
            textViewCash.text = ""
        }
    }

    fun calculateRenta(salary: Double?, rentaTable: List<RentaRange>): Double {
        if (salary == null) {
            return 0.0
        }

        for (range in rentaTable) {
            if (salary in range.start..range.end) {
                return (salary - range.excess) * range.percent + range.fixedFee
            }
        }

        return 0.0
    }

    data class RentaRange(val start: Double, val end: Double, val percent: Double, val fixedFee: Double, val excess: Double)
}
