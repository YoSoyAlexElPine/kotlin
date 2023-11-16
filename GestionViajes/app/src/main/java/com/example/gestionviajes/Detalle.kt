package com.example.gestionviajes

import Modelo.Almacen
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.gestionviajes.databinding.DetalleBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Actividad que muestra los detalles de un camión o empleado y permite editar o eliminar la información.
 * También permite actualizar los datos de Almacen.camiones o Almacen.empleados si se realizaron cambios.
 * @author Alex Pineño Sanchez
 */
class Detalle : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar y establecer la vista de la actividad
        var binding = DetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener datos del intent
        val nombre = intent.getStringExtra("nombre").toString()
        val marca = intent.getStringExtra("marca")
        val detalle = intent.getStringExtra("detalle")

        var usuario=""
        var bd=""
        var dato=""

        // Mostrar detalles en la interfaz gráfica
        binding.tvTitulo.isEnabled = false
        binding.tvTitulo.setText(nombre)

        binding.tvDetalle.isEnabled = false
        binding.tvDetalle.setText(detalle)

        // Establecer la imagen del camión/empleado
        val imageResource: Int = this.resources.getIdentifier(marca, null, this.packageName)
        val drawable: Drawable = this.resources.getDrawable(imageResource)
        binding.tvImagen.setImageDrawable(drawable)

        // Variables para controlar la colección y lista de almacenamiento
        lateinit var coleccion: String
        var almacen = Almacen.cards

        // Determinar el tipo de objeto y su colección correspondiente
        if (intent.getStringExtra("objeto") == "camion") {
            coleccion = "camiones"
            binding.tvTituloDetalle.text = "KM"

            bd="camiones"
            dato="km"

            almacen = Almacen.camiones
        } else if (intent.getStringExtra("objeto") == "empleado") {
            coleccion = "usuarios"
            binding.tvTituloDetalle.text = this.getString(R.string.Telefono)

            bd="usuarios"
            dato="telefono"

            almacen = Almacen.empleados
        }else if (intent.getStringExtra("objeto") == "viaje") {

            coleccion = "usuarios"
            usuario = intent.getStringExtra("usuario").toString()

            binding.tvTituloDetalle.text = this.getString(R.string.Direccion)
            binding.bEditarCard.visibility = View.GONE

        }

        // Acción al hacer clic en el botón "Eliminar"
        binding.bEliminarCard.setOnClickListener() {

            if (intent.getStringExtra("objeto") == "viaje") {
                eliminarViajeDeUsuario(usuario,nombre)
            } else {

                db.collection(coleccion).document(nombre.toString()).delete()
                almacen.removeIf { it.nombre == nombre }
            }
            finish()
        }

        // Acción al hacer clic en el botón "Cerrar"
        binding.bCerrarAsignar.setOnClickListener() {
            if (intent.getStringExtra("objeto") == "camion") {
                Almacen.camiones = almacen
            } else if (intent.getStringExtra("objeto") == "empleado") {
                Almacen.empleados = almacen
            }
            finish()
        }

        // Acción al hacer clic en el botón "Editar"
        binding.bEditarCard.setOnClickListener() {
            val id = binding.tvTitulo.text.toString()

            if (binding.bEditarCard.text == this.getString(R.string.Editar)) {
                binding.bEditarCard.text = this.getString(R.string.Confirmar)
                binding.tvDetalle.isEnabled = true
            } else {
                if (binding.bEditarCard.text == this.getString(R.string.Confirmar)) {
                    val nuevoDetalle = binding.tvDetalle.text.toString()

                    if (nuevoDetalle.isNullOrEmpty()) {
                        Toast.makeText(this, this.getString(R.string.IntroduceNombre), Toast.LENGTH_SHORT).show()
                    } else {
                        // Aquí se actualiza el campo "detalle" en Firestore
                        val documento = db.collection(bd).document(id)

                        documento.update(dato, nuevoDetalle)
                            .addOnSuccessListener {

                                Modelo.FactoriaCard.sincronizar(this)

                                binding.tvDetalle.isEnabled = false
                                binding.bEditarCard.text = this.getString(R.string.Editar)
                            }
                            .addOnFailureListener { exception ->
                                exception.printStackTrace()
                            }
                    }
                }
            }
        }

    }

    private fun eliminarViajeDeUsuario(idUsuario: String, nombreViaje: String) {
        val usuarioRef = db.collection("usuarios").document(idUsuario)

        usuarioRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val viajes = documentSnapshot.get("viajes") as? MutableList<Map<String, String>> ?: mutableListOf()

                val viajeEncontrado = viajes.find { it["localidad"] == nombreViaje }

                viajeEncontrado?.let {
                    viajes.remove(it)

                    usuarioRef.update("viajes", viajes)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Viaje eliminado correctamente", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error al eliminar el viaje", Toast.LENGTH_SHORT).show()
                        }
                } ?: run {
                    Toast.makeText(this, "No se encontró el viaje", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error al obtener el usuario", Toast.LENGTH_SHORT).show()
        }
    }
}
