package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





    }





    fun cambiarDesdeIzquierda(v: View){
        var textoCentral:TextView = findViewById(R.id.textView);
        textoCentral.text="Boton izquierdo";
    }
    fun cambiarDesdeDerecha(v: View){
        var textoCentral:TextView = findViewById(R.id.textView);
        textoCentral.text="Boton derecho";
    }
    fun cambiarDesdeCentro(v: View){
        var textoCentral:TextView = findViewById(R.id.textView);
        textoCentral.text="Boton central";
    }
}