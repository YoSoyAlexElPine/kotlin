package com.example.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textoResultado: TextView = findViewById(R.id.texto_resultado)
        var textoResto: TextView = findViewById(R.id.texto_resto)
        var numero1: EditText = findViewById(R.id.edText_numero1)
        var numero2: EditText = findViewById(R.id.edText_numero2)

        var botonSumar: Button = findViewById(R.id.boton_sumar)
        var botonRestar: Button = findViewById(R.id.boton_restar)
        var botonDividir: Button = findViewById(R.id.boton_dividir)
        var botonMultiplicar: Button = findViewById(R.id.boton_multiplicar)


        botonSumar.setOnClickListener {
            try {
                val numeroValor1 = numero1.text.toString().toDouble()
                val numeroValor2 = numero2.text.toString().toDouble()
                val operacion = numeroValor1 + numeroValor2
                textoResultado.text = operacion.toString()
                textoResto.text="-";
            } catch (e: NumberFormatException) {
                textoResultado.text = "Introduce números válidos"
            }
        }

        botonRestar.setOnClickListener {
            try {
                val numeroValor1 = numero1.text.toString().toDouble()
                val numeroValor2 = numero2.text.toString().toDouble()
                val operacion = numeroValor1 - numeroValor2
                textoResultado.text = operacion.toString()
                textoResto.text="-";
            } catch (e: NumberFormatException) {
                textoResultado.text = "Introduce números válidos"
            }
        }

        botonMultiplicar.setOnClickListener {
            try {
                val numeroValor1 = numero1.text.toString().toDouble()
                val numeroValor2 = numero2.text.toString().toDouble()
                val operacion = numeroValor1 * numeroValor2
                textoResultado.text = operacion.toString()
                textoResto.text="-";
            } catch (e: NumberFormatException) {
                textoResultado.text = "Introduce números válidos"
            }
        }

        botonDividir.setOnClickListener {
            try {
                val numeroValor1 = numero1.text.toString().toDouble()
                val numeroValor2 = numero2.text.toString().toDouble()
                if (numeroValor2 != 0.0) {
                    val division = numeroValor1 / numeroValor2
                    textoResultado.text = division.toString()
                    val resto = numeroValor1 % numeroValor2
                    textoResto.text = resto.toString()
                } else {
                    textoResultado.text = "No se puede dividir por cero"
                }
            } catch (e: NumberFormatException) {
                textoResultado.text = "Introduce números válidos"
            }
        }
    }
}
