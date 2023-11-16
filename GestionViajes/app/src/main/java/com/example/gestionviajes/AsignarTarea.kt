package com.example.gestionviajes

import Modelo.Card
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gestionviajes.databinding.AsignarTareaBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Actividad para asignar un viaje a un usuario en Firestore.
 * @property db Instancia de FirebaseFirestore para acceder a la base de datos Firestore.
 */
class AsignarTarea : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        var b = AsignarTareaBinding.inflate(layoutInflater)
        setContentView(b.root);


        b.bEnviar.setOnClickListener(){
            // Llamar a la función para asignar un viaje a un usuario
            asignarViajeAUsuario(b.tbDestinatario.text.toString(), Card(b.tbLocalidad.text.toString(),"drawable/fantasma",
                Intent(this,Detalle::class.java), b.tbDireccion.text.toString()))

        }

        b.bCerrarAsignar.setOnClickListener {
            finish()
        }

    }


    /**
     * Función para asignar un viaje a un usuario en Firestore.
     * @param idUsuario El ID del usuario al que se le asignará el viaje.
     * @param viaje Objeto Viaje que se asignará al usuario.
     */
    private fun asignarViajeAUsuario(idUsuario: String, viaje: Card) {
        val usuarioRef = db.collection("usuarios").document(idUsuario)

        usuarioRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val isAdmin = documentSnapshot.getBoolean("admin") ?: false
                if (isAdmin) {
                    Toast.makeText(this, this.getString(R.string.UsuarioAdminNoViaje), Toast.LENGTH_SHORT).show()
                } else {
                    val viajes = documentSnapshot.get("viajes") as? MutableList<Map<String, String>> ?: mutableListOf()
                    viajes.add(mapOf("localidad" to viaje.nombre, "direccion" to viaje.detalle))

                    usuarioRef.update("viajes", viajes)
                        .addOnSuccessListener {
                            Toast.makeText(this, this.getString(R.string.AsignarViaje), Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, this.getString(R.string.AsignarViajeError), Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                // El usuario no existe, muestra un mensaje de error
                Toast.makeText(this, this.getString(R.string.UsuarioNoExiste), Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            // Error al obtener el documento del usuario
            Toast.makeText(this, this.getString(R.string.ErrorNombre), Toast.LENGTH_SHORT).show()
        }
    }

}