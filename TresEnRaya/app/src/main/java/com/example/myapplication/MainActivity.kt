package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var b=ActivityMainBinding.inflate(layoutInflater);
        setContentView(b.root)

        var pulsado:Boolean;
        var turno:Int;
        turno=0

        b.ib1.setOnClickListener {
            if (turno % 2 == 0) {
                b.ib1.setImageResource(R.drawable.cruz)
            } else {
                b.ib1.setImageResource(R.drawable.circulo)
            }
            turno++ // Incrementa el turno
        }

// Repite el mismo patrón para otros ImageButtons
        b.ib2.setOnClickListener {
            if (turno % 2 == 0) {
                b.ib2.setImageResource(R.drawable.cruz)
            } else {
                b.ib2.setImageResource(R.drawable.circulo)
            }
            turno++
        }
        b.ib3.setOnClickListener {
            if (turno % 2 == 0) {
                b.ib3.setImageResource(R.drawable.cruz)
            } else {
                b.ib3.setImageResource(R.drawable.circulo)
            }
            turno++
        }
        b.ib4.setOnClickListener {
            if (turno % 2 == 0) {
                b.ib4.setImageResource(R.drawable.cruz)
            } else {
                b.ib4.setImageResource(R.drawable.circulo)
            }
            turno++
        }
        b.ib5.setOnClickListener {
            if (turno % 2 == 0) {
                b.ib5.setImageResource(R.drawable.cruz)
            } else {
                b.ib5.setImageResource(R.drawable.circulo)
            }
            turno++
        }
        b.ib6.setOnClickListener {
            if (turno % 2 == 0) {
                b.ib6.setImageResource(R.drawable.cruz)
            } else {
                b.ib6.setImageResource(R.drawable.circulo)
            }
            turno++
        }
        b.ib7.setOnClickListener {
            if (turno % 2 == 0) {
                b.ib7.setImageResource(R.drawable.cruz)
            } else {
                b.ib7.setImageResource(R.drawable.circulo)
            }
            turno++
        }
        b.ib8.setOnClickListener {
            if (turno % 2 == 0) {
                b.ib8.setImageResource(R.drawable.cruz)
            } else {
                b.ib8.setImageResource(R.drawable.circulo)
            }
            turno++
        }
        b.ib9.setOnClickListener {
            if (turno % 2 == 0) {
                b.ib9.setImageResource(R.drawable.cruz)
            } else {
                b.ib9.setImageResource(R.drawable.circulo)
            }
            turno++
        }


        b.button.setOnClickListener(){

            b.ib1.setImageResource(R.drawable.blanco);
            b.ib2.setImageResource(R.drawable.blanco);
            b.ib3.setImageResource(R.drawable.blanco);
            b.ib4.setImageResource(R.drawable.blanco);
            b.ib5.setImageResource(R.drawable.blanco);
            b.ib6.setImageResource(R.drawable.blanco);
            b.ib7.setImageResource(R.drawable.blanco);
            b.ib8.setImageResource(R.drawable.blanco);
            b.ib9.setImageResource(R.drawable.blanco);


        }

    }




    private fun verificarGanador(): Boolean {
        // Implementa la lógica de verificación de ganador aquí
        // Comprueba las filas, columnas y diagonales para determinar si hay un ganador.
        // Puedes comparar las etiquetas (tags) de las celdas para verificar si coinciden.
        // Si encuentras un ganador, puedes mostrar un mensaje y establecer "juegoTerminado" en true.
        return false
    }

}