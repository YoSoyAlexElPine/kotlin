package com.example.gestionviajes

import Modelo.Card
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.gestionviajes.Notificacion.mostrarNotificacion
import com.example.gestionviajes.databinding.RegistarNuevoUsuarioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Actividad para registrar nuevos usuarios.
 * Permite crear nuevos usuarios y almacenar sus datos en Firestore.
 * @author Alex Pineño Sanchez
 */
class RegistarNuevoUsuario : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private var fa = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val b = RegistarNuevoUsuarioBinding.inflate(layoutInflater)
        setContentView(b.root)

        // Inicialmente, oculta el campo de administrador hasta que se active la opción correspondiente
        b.admin.visibility = View.INVISIBLE

        // Mostrar el campo de contraseña para administradores si se activa la opción de administrador
        b.sAdministrador.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                b.admin.visibility = View.VISIBLE
            } else {
                b.tbContrasenaAdministrador.setText("")
                b.admin.visibility = View.INVISIBLE
            }
        }

        // Crear un nuevo usuario
        b.bCrearUsuario.setOnClickListener {
            if (b.tbMail.text.isNullOrEmpty() || b.tbClave.text.isNullOrEmpty() || b.tbTelefono.text.isNullOrEmpty() || b.tbApodo.text.isNullOrEmpty()) {
                // Verificar si algún campo está vacío y mostrar un mensaje
                Toast.makeText(this, this.getString(R.string.RellenaCampos), Toast.LENGTH_SHORT).show()
            } else {

                val mail = b.tbMail.text.toString()
                val telefono = b.tbTelefono.text.toString()
                val clave = b.tbClave.text.toString()
                val nombre = b.tbApodo.text.toString()

                // Verificar si el correo ya está en uso en la colección 'usuarios'
                db.collection("usuarios")
                    .whereEqualTo("mail", mail)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            Toast.makeText(this, this.getString(R.string.MailEnUso), Toast.LENGTH_SHORT).show()
                        } else {
                            // Verificar si el nombre ya está en uso en la colección 'usuarios'
                            db.collection("usuarios")
                                .whereEqualTo("nombre", nombre)
                                .get()
                                .addOnSuccessListener { documents ->
                                    if (!documents.isEmpty) {
                                        Toast.makeText(this, this.getString(R.string.NombreEnUso), Toast.LENGTH_SHORT).show()
                                    } else {
                                        // Almacenar los datos del nuevo usuario en Firestore
                                        db.collection("usuarios").document(b.tbApodo.text.toString()).set(
                                            hashMapOf(
                                                "mail" to mail,
                                                "nombre" to nombre,
                                                "telefono" to telefono,
                                                "clave" to clave,
                                                "admin" to b.sAdministrador.isChecked,
                                            )
                                        )

                                        val card = Card(nombre,"@drawable/empleado",
                                            Intent(this,Detalle::class.java),telefono
                                        )

                                        mostrarNotificacion(this,this.getString(R.string.UsuarioCreado),this.getString(R.string.UsuarioCreadoContenido)+"\n\n"+this.getString(R.string.UsuarioCreadoContenido2),card)
                                        Toast.makeText(this, this.getString(R.string.UsuarioCreado), Toast.LENGTH_SHORT).show()
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    // Manejar errores al obtener documentos
                                    exception.printStackTrace()
                                    Toast.makeText(this, this.getString(R.string.ErrorNombre), Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Manejar errores al obtener documentos
                        exception.printStackTrace()
                        Toast.makeText(this, this.getString(R.string.ErroMail), Toast.LENGTH_SHORT).show()
                    }
            }
        }

        // Cerrar la actividad de registro de usuario
        b.bCerrarSesionCrearUsuario.setOnClickListener {
            finish()
        }
    }
}
