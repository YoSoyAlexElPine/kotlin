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
            if(b.tbMail.text.isNullOrEmpty()&&b.tbClave.text.isNullOrEmpty()&&b.tbNombreCompleto.text.isNullOrEmpty()&&b.tbApodo.text.isNullOrEmpty()){
                Toast.makeText(this,"Rellena los campos",Toast.LENGTH_SHORT).show()
            }else{

                val claves = db.collection("claveAdmin")

                claves.get()
                    .addOnSuccessListener { documents ->
                        Almacen.adminClaves.clear()
                        for (document in documents) {
                            Almacen.adminClaves.add(document.getString("clave").toString())
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Manejar errores al obtener documentos
                        exception.printStackTrace()
                        Toast.makeText(this,"error",Toast.LENGTH_SHORT).show()
                    }

                Almacen.adminClaves.add("admin")

                if (b.sAdministrador.isChecked && !b.tbContrasenaAdministrador.text.isNullOrEmpty()){




                    // Verificar si es administrador
                    if(Almacen.adminClaves.contains(b.tbContrasenaAdministrador.text.toString())){
                        // Crear administrador
                        db.collection("usuarios").document(b.tbApodo.text.toString()).set(
                            hashMapOf(
                                "mail" to b.tbMail.text.toString(),
                                "nombre" to b.tbNombreCompleto.text.toString(),
                                "admin" to true,
                            )
                        )

                        db.collection("claveAdmin").document(b.tbApodo.text.toString()).set(
                            hashMapOf(
                                "clave" to b.tbClave.text.toString(),
                            )
                        )

                        fa.createUserWithEmailAndPassword(b.tbMail.text.toString(),b.tbClave.text.toString())


                        Toast.makeText(this,"creado",Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this,"Administrador incorrecto",Toast.LENGTH_SHORT).show()
                    }
                } else {

                    // Crear empleado

                    fa.createUserWithEmailAndPassword(b.tbMail.text.toString(),b.tbClave.text.toString()).addOnCompleteListener {
                        if (it.isSuccessful){
                              //Esto de los interrogantes es por si está vacío el email, que enviaría una cadena vacía.
                        } else {
                            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener{
                        Toast.makeText(this, "Conexión no establecida", Toast.LENGTH_SHORT).show()
                    }


                    db.collection("usuarios").document(b.tbApodo.text.toString()).set(
                        hashMapOf(
                            "mail" to b.tbMail.text.toString(),
                            "nombre" to b.tbNombreCompleto.text.toString(),
                            "admin" to false,
                        )
                    )

                    Toast.makeText(this, "creado", Toast.LENGTH_SHORT).show()
                }
            }
        }

        b.bCerrarSesionCrearUsuario.setOnClickListener (){
            finish()
        }


    }
}