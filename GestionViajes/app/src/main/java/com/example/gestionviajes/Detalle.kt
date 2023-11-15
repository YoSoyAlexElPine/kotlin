package com.example.gestionviajes

import Modelo.Almacen
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
        val nombre = intent.getStringExtra("nombre")
        val marca = intent.getStringExtra("marca")
        val detalle = intent.getStringExtra("detalle")

        // Mostrar detalles en la interfaz gráfica
        binding.tvTitulo.isEnabled = false
        binding.tvTitulo.setText(nombre)
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
            almacen = Almacen.camiones
        } else if (intent.getStringExtra("objeto") == "empleado") {
            coleccion = "empleados"
            binding.tvTituloDetalle.text = this.getString(R.string.Telefono)
            almacen = Almacen.empleados
        }

        // Acción al hacer clic en el botón "Eliminar"
        binding.bEliminarCard.setOnClickListener() {
            db.collection(coleccion).document(nombre.toString()).delete()
            almacen.removeIf { it.nombre == nombre }
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
                binding.tvTitulo.isEnabled = true
            } else {
                if (binding.bEditarCard.text == this.getString(R.string.Confirmar)) {
                    if (binding.tvTitulo.text.isNullOrEmpty()) {
                        Toast.makeText(this, this.getString(R.string.IntroduceNombre), Toast.LENGTH_SHORT).show()
                    } else {
                        binding.tvTitulo.isEnabled = false
                        binding.bEditarCard.text = this.getString(R.string.Editar)
                    }
                }
            }
        }
    }
}
