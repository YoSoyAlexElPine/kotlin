package com.example.gestionviajes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gestionviajes.databinding.AsignarTareaBinding
import com.example.gestionviajes.databinding.InicioEmpleadoBinding

class InicioEmpleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio_empleado)


        var b = InicioEmpleadoBinding.inflate(layoutInflater)
        setContentView(b.root);



        b.bCerrarSesion.setOnClickListener {
            finish()
        }
    }
}