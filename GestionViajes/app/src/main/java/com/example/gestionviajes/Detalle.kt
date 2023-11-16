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

class Detalle : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = DetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("nombre").toString()
        val marca = intent.getStringExtra("marca")
        val detalle = intent.getStringExtra("detalle")

        var usuario = ""
        var bd = ""
        var dato = ""

        binding.tvTitulo.isEnabled = false
        binding.tvTitulo.setText(nombre)

        binding.tvDetalle.isEnabled = false
        binding.tvDetalle.setText(detalle)

        val imageResource: Int = this.resources.getIdentifier(marca, null, this.packageName)
        val drawable: Drawable = this.resources.getDrawable(imageResource)
        binding.tvImagen.setImageDrawable(drawable)

        lateinit var coleccion: String
        var almacen = Almacen.cards

        if (intent.getStringExtra("objeto") == "camion") {
            coleccion = "camiones"
            binding.tvTituloDetalle.text = "KM"

            bd = "camiones"
            dato = "km"

            almacen = Almacen.camiones
        } else if (intent.getStringExtra("objeto") == "empleado") {
            coleccion = "usuarios"
            binding.tvTituloDetalle.text = this.getString(R.string.Telefono)

            bd = "usuarios"
            dato = "telefono"

            almacen = Almacen.empleados
        } else if (intent.getStringExtra("objeto") == "viaje") {

            coleccion = "usuarios"
            usuario = intent.getStringExtra("usuario").toString()

            binding.tvTituloDetalle.text = this.getString(R.string.Direccion)
            binding.bEditarCard.visibility = View.GONE
        }

        binding.bEliminarCard.setOnClickListener() {
            if (intent.getStringExtra("objeto") == "viaje") {
                eliminarViajeDeUsuario(usuario, nombre)

            } else {
                db.collection(coleccion).document(nombre.toString()).delete()
                almacen.removeIf { it.nombre == nombre }
                finish()
            }

        }

        binding.bCerrarAsignar.setOnClickListener() {
            if (intent.getStringExtra("objeto") == "camion") {
                Almacen.camiones = almacen
            } else if (intent.getStringExtra("objeto") == "empleado") {
                Almacen.empleados = almacen
            }
            finish()
        }

        binding.bEditarCard.setOnClickListener() {
            val id = binding.tvTitulo.text.toString()

            if (binding.bEditarCard.text == this.getString(R.string.Editar)) {
                binding.bEditarCard.text = this.getString(R.string.Confirmar)
                binding.tvDetalle.isEnabled = true
            } else {
                if (binding.bEditarCard.text == this.getString(R.string.Confirmar)) {
                    val nuevoDetalle = binding.tvDetalle.text.toString()

                    if (nuevoDetalle.isNullOrEmpty()) {
                        Toast.makeText(
                            this,
                            this.getString(R.string.IntroduceNombre),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
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
            val viajes = documentSnapshot.get("viajes") as? MutableList<Map<String, String>>
                ?: mutableListOf()

            val viajeEncontrado = viajes.find { it["localidad"] == nombreViaje }

            viajeEncontrado?.let {
                viajes.remove(it)

                usuarioRef.update("viajes", viajes)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Viaje eliminado correctamente", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al eliminar el viaje", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }
            } ?: run {
                Toast.makeText(this, "No se encontró el viaje", Toast.LENGTH_SHORT).show()
                finish()
            }

        }
    }
}
