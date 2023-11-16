package com.descuentosv.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
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

        val salary = editTextSalary.text.toString().toDoubleOrNull()

        if (salary != null) {
            val afp = salary * 0.075
            val isss = salary * 0.03
            val totalDeductions = afp + isss
            val taxRate = 0.1 // Tasa de impuesto fija del 10%
            val renta = if (salary > 1000) salary * taxRate else 0.0
            val netSalary = salary - totalDeductions - renta
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
