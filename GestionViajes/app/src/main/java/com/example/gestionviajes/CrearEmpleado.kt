package com.example.gestionviajes

import Modelo.Almacen
import Modelo.Card
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gestionviajes.databinding.CrearEmpleadoBinding
import com.google.firebase.firestore.FirebaseFirestore

class CrearEmpleado : AppCompatActivity() {
    private val db= FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var b = CrearEmpleadoBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.bCrearCamion.setOnClickListener(){
            if (!b.tbNombre.text.isNullOrEmpty() && !b.tbTelefono.text.isNullOrEmpty()){

                if(!Almacen.empleados.any {it.titulo == b.tbNombre.text.toString()}) {

                    try {
                        db.collection("empleados").document(b.tbNombre.text.toString()).set(hashMapOf("nombre" to b.tbNombre.text.toString(),
                            "telefono" to b.tbTelefono.text.toString()))
                        Almacen.empleados.add(
                            Card(
                                b.tbNombre.text.toString(),
                                "@drawable/empleado",
                                Intent(this, Detalle::class.java),
                                b.tbTelefono.text.toString()
                                )
                            )

                        b.tbNombre.setText("")

                    } catch (e: Exception) {

                        db.collection("empleados").document(b.tbNombre.text.toString()).set(hashMapOf(
                            "nombre" to b.tbNombre.text.toString(),
                            "telefono" to b.tbTelefono.text.toString()))

                        Almacen.empleados.add(
                            Card(
                                b.tbNombre.text.toString(),
                                "@drawable/empleado",
                                Intent(this, Detalle::class.java),
                                b.tbTelefono.text.toString()
                            )
                        )
                        b.tbNombre.setText("")
                    }
                }else{
                    val mensaje = b.tbNombre.text.toString()+" ya existe"
                    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                }
            }

        }

        b.bCerrarAsignar.setOnClickListener {
            finish()
        }

    }
}