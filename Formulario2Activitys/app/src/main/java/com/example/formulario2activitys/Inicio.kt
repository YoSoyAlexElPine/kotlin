package com.example.formulario2activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.formulario2activitys.databinding.ActivityMainBinding

class Inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var b=ActivityMainBinding.inflate(layoutInflater);
        setContentView(b.root)

        b.btAceptar.setOnClickListener {

            if (validarVacio() && b.etContrasena.text.length>=6 && b.etContrasena.text==b.etContrasenaConfirm){
                val intent = Intent(this, Salida::class.java)
                startActivity(intent)
            }

        }

    }

    private fun validarVacio(): Boolean {

        var b=ActivityMainBinding.inflate(layoutInflater);
        setContentView(b.root)

        var retorno:Boolean

        retorno=false

        if (b.etNombre.text.isNotEmpty() && b.etApellido.text.isNotEmpty()&& b.etContrasena.text.isNotEmpty()&& b.etMail.text.isNotEmpty() && b.etDni.text.isNotEmpty()&& b.etContrasenaConfirm.text.isNotEmpty()){
            retorno=true
        } else {
            val toast = Toast.makeText(applicationContext, "Rellena todos los campos", Toast.LENGTH_SHORT)
            toast.show()
        }


        return retorno
    }

}