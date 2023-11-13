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
    private lateinit var fa : FirebaseAuth
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

                if (b.sAdministrador.isActivated && !b.tbContrasenaAdministrador.text.isNullOrEmpty()){

                    // Verificar si es administrador
                    if(b.tbContrasenaAdministrador.text.toString() in Almacen.adminClaves){
                        // Crear administrador
                        db.collection("usuarios").document(b.tbApodo.text.toString()).set(
                            hashMapOf(
                                "mail" to b.tbMail.text.toString(),
                                "nombre" to b.tbNombreCompleto.text.toString(),
                                "admin" to true,
                            )
                        )

                        fa.signInWithEmailAndPassword(
                            b.tbMail.text.toString(),
                            b.tbClave.text.toString()
                        )

                        Toast.makeText(this,"creado",Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this,"Administrador incorrecto",Toast.LENGTH_SHORT).show()
                    }



                }

                // Crear empleado

                fa.signInWithEmailAndPassword(
                    b.tbMail.text.toString(),
                    b.tbClave.text.toString()
                )

                db.collection("usuarios").document(b.tbApodo.text.toString()).set(
                    hashMapOf(
                        "mail" to b.tbMail.text.toString(),
                        "nombre" to b.tbNombreCompleto.text.toString(),
                        "admin" to false,
                    )
                )

                Toast.makeText(this,"creado",Toast.LENGTH_SHORT).show()

            }
        }

        b.bCerrarSesionCrearUsuario.setOnClickListener (){
            finish()
        }


    }
}