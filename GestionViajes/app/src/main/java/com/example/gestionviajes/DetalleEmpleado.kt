package com.example.gestionviajes

import Modelo.Almacen
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gestionviajes.databinding.DetalleCamionBinding
import com.example.gestionviajes.databinding.DetalleEmpleadoBinding
import com.google.firebase.firestore.FirebaseFirestore

class DetalleEmpleado : AppCompatActivity() {
    private val db= FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var b = DetalleEmpleadoBinding.inflate(layoutInflater)
        setContentView(b.root)

        var nombre = intent.getStringExtra("nombre")
        var marca = intent.getStringExtra("marca")

        b.tvTitulo.text=nombre

        val imageResource: Int = this.resources.getIdentifier(marca, null, this.packageName)
        var res: Drawable = this.resources.getDrawable(imageResource)

        b.tvImagen.setImageDrawable(res)

        b.bEliminarCard.setOnClickListener(){

            db.collection("empleados").document(nombre.toString()).delete()

            Almacen.empleados.removeIf { it.titulo == nombre}

            finish()
        }

        b.bCerrarAsignar.setOnClickListener(){
            finish()
        }

    }
}