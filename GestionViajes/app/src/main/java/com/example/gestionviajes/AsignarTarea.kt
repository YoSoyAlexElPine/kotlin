package com.example.gestionviajes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gestionviajes.databinding.AsignarTareaBinding

class AsignarTarea : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var b = AsignarTareaBinding.inflate(layoutInflater)
        setContentView(b.root);



        b.bCerrarAsignar.setOnClickListener {
            finish()
        }

    }
}