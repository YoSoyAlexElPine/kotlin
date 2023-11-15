package com.example.gestionviajes

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gestionviajes.databinding.AsignarTareaBinding
import com.example.gestionviajes.databinding.InicioEmpleadoBinding
import com.google.firebase.auth.FirebaseAuth

class InicioEmpleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.inicio_empleado)


        var b = InicioEmpleadoBinding.inflate(layoutInflater)
        setContentView(b.root);



        b.bCerrarSesion.setOnClickListener {
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            finish()
        }
    }
}