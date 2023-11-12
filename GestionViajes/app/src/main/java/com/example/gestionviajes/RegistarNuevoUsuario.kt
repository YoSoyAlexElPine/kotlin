package com.example.gestionviajes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.gestionviajes.databinding.RegistarNuevoUsuarioBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class RegistarNuevoUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var b=RegistarNuevoUsuarioBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.admin.visibility= View.INVISIBLE

        b.sAdministrador.setOnCheckedChangeListener { _, a ->
            if(b.sAdministrador.isChecked){
                b.admin.visibility = View.VISIBLE
            } else {
                b.tbContrasenaAdministrador.setText("")
                b.admin.visibility = View.INVISIBLE
            }

        }

        b.bCerrarSesionCrearUsuario.setOnClickListener (){
            finish()
        }


    }
}