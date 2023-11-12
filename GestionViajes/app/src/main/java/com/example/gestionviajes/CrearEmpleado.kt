package com.example.gestionviajes

import Modelo.Almacen
import Modelo.Card
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gestionviajes.databinding.CrearCamionBinding
import com.example.gestionviajes.databinding.CrearEmpleadoBinding

class CrearEmpleado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var b = CrearEmpleadoBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.bCrearCamion.setOnClickListener(){
            if (!b.tbNombre.text.isNullOrEmpty()){

                if(!Almacen.empleados.any {it.titulo == b.tbNombre.text.toString()}) {

                    try {

                        Almacen.empleados.add(
                            Card(
                                b.tbNombre.text.toString(),
                                "@drawable/empleado",
                                Intent(this, DetalleEmpleado::class.java)
                            )
                        )

                        b.tbNombre.setText("")

                    } catch (e: Exception) {
                        Almacen.empleados.add(
                            Card(
                                b.tbNombre.text.toString(),
                                "@drawable/empleado",
                                Intent(this, DetalleEmpleado::class.java)
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