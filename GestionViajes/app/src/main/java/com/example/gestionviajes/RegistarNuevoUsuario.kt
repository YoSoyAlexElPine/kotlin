package com.example.gestionviajes

import Modelo.Almacen
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.gestionviajes.databinding.RegistarNuevoUsuarioBinding
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistarNuevoUsuario : AppCompatActivity() {
    private val db=FirebaseFirestore.getInstance()
    var fa = FirebaseAuth.getInstance()
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

        b.bCrearUsuario.setOnClickListener(){
            if(b.tbMail.text.isNullOrEmpty() || b.tbClave.text.isNullOrEmpty() || b.tbNombreCompleto.text.isNullOrEmpty() || b.tbApodo.text.isNullOrEmpty()){
                Toast.makeText(this,"Rellena los campos",Toast.LENGTH_SHORT).show()
            } else {
                val mail = b.tbMail.text.toString()
                val nombre = b.tbNombreCompleto.text.toString()

                // Verificar si el mail ya está en uso en la colección 'usuarios'
                db.collection("usuarios")
                    .whereEqualTo("mail", mail)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            Toast.makeText(this, "El mail ya está en uso", Toast.LENGTH_SHORT).show()
                        } else {
                            // Verificar si el nombre ya está en uso en la colección 'usuarios'
                            db.collection("usuarios")
                                .whereEqualTo("nombre", nombre)
                                .get()
                                .addOnSuccessListener { documents ->
                                    if (!documents.isEmpty) {
                                        Toast.makeText(this, "El nombre ya está en uso", Toast.LENGTH_SHORT).show()
                                    } else {
                                        // Aquí puedes continuar con la lógica para crear el usuario
                                        // ...

                                        // Por ejemplo:
                                        // Crear el usuario con los datos proporcionados
                                        db.collection("usuarios").document(b.tbApodo.text.toString()).set(
                                            hashMapOf(
                                                "mail" to mail,
                                                "nombre" to nombre,
                                                "admin" to b.sAdministrador.isChecked,
                                                // Otros campos que desees guardar
                                            )
                                        )

                                        // También puedes realizar otras operaciones aquí, como crear el usuario en la autenticación de Firebase
                                        // ...

                                        // Mostrar mensaje de éxito
                                        Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    // Manejar errores al obtener documentos
                                    exception.printStackTrace()
                                    Toast.makeText(this,"Error al verificar el nombre",Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Manejar errores al obtener documentos
                        exception.printStackTrace()
                        Toast.makeText(this,"Error al verificar el mail",Toast.LENGTH_SHORT).show()
                    }
            }
        }


        b.bCerrarSesionCrearUsuario.setOnClickListener (){
            finish()
        }


    }
}