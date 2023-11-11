package com.example.gestionviajes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.gestionviajes.databinding.RegistarNuevoUsuarioBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class RegistarNuevoUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var b=RegistarNuevoUsuarioBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.admin.isVisible=false

        b.sAdministrador.setOnCheckedChangeListener { _, a ->
            b.admin.isVisible = b.sAdministrador.isChecked
        }

        b.bCerrarSesionCrearUsuario.setOnClickListener (){
            finish()
        }


    }
}