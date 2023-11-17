package com.example.gestionviajes

import Modelo.Almacen
import Modelo.Card
import android.app.Notification
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gestionviajes.Notificacion.mostrarNotificacion
import com.example.gestionviajes.databinding.CrearEmpleadoBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Actividad para crear un nuevo empleado y almacenarlo en Firestore y Almacen.empleados.
 * Permite agregar un nuevo empleado con su nombre y teléfono.
 * @author Alex Pineño Sanchez
 */
class CrearEmpleado : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var b = CrearEmpleadoBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.bCrearCamion.setOnClickListener() {
            if (!b.tbNombre.text.isNullOrEmpty() && !b.tbTelefono.text.isNullOrEmpty()) {

                if (!Almacen.empleados.any { it.nombre == b.tbNombre.text.toString() }) {
                    try {

                        val card = Card(
                            b.tbNombre.text.toString(),
                            "@drawable/empleado",
                            Intent(this, Detalle::class.java),
                            b.tbTelefono.text.toString()
                        )

                        // Guardar en Firestore
                        db.collection("empleados").document(b.tbNombre.text.toString()).set(
                            hashMapOf(
                                "nombre" to b.tbNombre.text.toString(),
                                "telefono" to b.tbTelefono.text.toString()
                            )
                        )

                        // Agregar a Almacen.empleados
                        Almacen.empleados.add(card)

                        mostrarNotificacion(this,this.getString(R.string.UsuarioCreado),this.getString(R.string.UsuarioCreadoContenido)+"\n\n"+this.getString(R.string.UsuarioCreadoContenido2),card)

                        b.tbNombre.setText("")
                    } catch (e: Exception) {
                        Toast.makeText(this,this.getString(R.string.ErrorNombre),Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Mostrar mensaje si el empleado ya existe
                    val mensaje = b.tbNombre.text.toString() + " " + this.getString(R.string.YaExiste)
                    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Acción al hacer clic en el botón "Cerrar"
        b.bCerrarAsignar.setOnClickListener {
            finish()
        }
    }
}
