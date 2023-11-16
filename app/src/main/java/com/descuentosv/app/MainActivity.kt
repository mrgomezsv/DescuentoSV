package com.descuentosv.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {
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
                else -> 0 // Este caso no debería ocurrir ahora
            }

            val afpPercentage = if (selectedDays == 15) 0.05 else 0.075
            val isssPercentage = if (selectedDays == 15) 0.02 else 0.03

            val afp = salary * afpPercentage
            val isss = salary * isssPercentage
            val totalDeductions = afp + isss

            val taxRate = 0.1
            val renta = if ((selectedDays == 15 || selectedDays == 30) && salary > 1000) salary * taxRate else 0.0

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
}
