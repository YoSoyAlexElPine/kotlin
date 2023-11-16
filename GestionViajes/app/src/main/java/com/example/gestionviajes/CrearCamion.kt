package com.example.gestionviajes

import AuxiliarDB.Conexion
import Modelo.Almacen
import Modelo.Card
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gestionviajes.databinding.CrearCamionBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Actividad para crear un nuevo camión y almacenarlo en Firestore y Almacen.camiones.
 * Permite agregar un nuevo camión con sus detalles, como chofer, nombre, marca y kilometraje.
 * @author Alex Pineño Sanchez
 */
class CrearCamion : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var b = CrearCamionBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.bCrearCamion.setOnClickListener() {
            if (!b.tbChofer.text.isNullOrEmpty() &&
                !b.tbMarca.text.isNullOrEmpty() &&
                !b.tbNombre.text.isNullOrEmpty() &&
                !b.tbKm.text.isNullOrEmpty()
            ) {

                var km = b.tbKm.text.toString()

                var marca = b.tbMarca.text.toString().trim().toLowerCase()

                if (!Almacen.camiones.any { it.nombre == b.tbNombre.text.toString() }) {
                    try {
                        // Guardar en Firestore
                        /*db.collection("camiones").document(nombre).set(
                            hashMapOf(
                                "chofer" to chofer,
                                "marca" to marca,
                                "km" to km
                            )
                        )*/

                        val card=Card(
                            b.tbNombre.text.toString(),
                            "@drawable/" + marca,
                            Intent(this, Detalle::class.java),
                            km
                        )

                        // Guardar en SQLite
                        Conexion.addCard(this,card)

                        // Agregar a Almacen.camiones
                        Almacen.camiones.add(card)
                        b.tbNombre.setText("")
                    } catch (e: Exception) {
                        // Manejo de excepciones al guardar en Firestore
                        /*db.collection("camiones").document(nombre).set(
                            hashMapOf(
                                "chofer" to chofer,
                                "marca" to marca,
                                "km" to km
                            )
                        )*/


                        val card = Card(
                            b.tbNombre.text.toString(),
                            "@drawable/camion2",
                            Intent(this, Detalle::class.java),
                            km
                        )

                        Conexion.addCard(this,card)

                        Almacen.camiones.add(card)
                        b.tbNombre.setText("")
                    }
                } else {
                    // Mostrar mensaje si el camión ya existe
                    val mensaje =
                        b.tbNombre.text.toString() + " " + this.getString(R.string.YaExiste)
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
