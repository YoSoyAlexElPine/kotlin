package com.example.gestionviajes

import Modelo.Almacen
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gestionviajes.databinding.DetalleBinding
import com.google.firebase.firestore.FirebaseFirestore

class Detalle : AppCompatActivity() {
    private val db= FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var b = DetalleBinding.inflate(layoutInflater)
        setContentView(b.root)


        var nombre = intent.getStringExtra("nombre")
        var marca = intent.getStringExtra("marca")
        var detalle = intent.getStringExtra("detalle")

        b.tvTitulo.isEnabled=false
        b.tvTitulo.setText(nombre)
        b.tvDetalle.setText(detalle)

        val imageResource: Int = this.resources.getIdentifier(marca, null, this.packageName)
        var res: Drawable = this.resources.getDrawable(imageResource)

        b.tvImagen.setImageDrawable(res)

        lateinit var coleccion:String
        var almacen=Almacen.cards

        if(intent.getStringExtra("objeto")=="camion"){
            coleccion="camiones"

            b.tvTituloDetalle.text="KM"

            almacen=Almacen.camiones
        }

        if(intent.getStringExtra("objeto")=="empleado"){
            coleccion="empleados"

            b.tvTituloDetalle.text=this.getString(R.string.Telefono)

            almacen=Almacen.empleados
        }

        b.bEliminarCard.setOnClickListener(){

            db.collection(coleccion).document(nombre.toString()).delete()

            almacen.removeIf { it.titulo == nombre}

            finish()
        }

        b.bCerrarAsignar.setOnClickListener(){
            if(intent.getStringExtra("objeto")=="camion"){
                Almacen.camiones=almacen
            }
            if(intent.getStringExtra("objeto")=="empleado"){
                Almacen.empleados=almacen
            }
            finish()
        }

        b.bEditarCard.setOnClickListener(){

            var id=b.tvTitulo.text.toString()

            if(b.bEditarCard.text=="Editar"){
                b.bEditarCard.text="Confirmar"
                b.tvTitulo.isEnabled=true
            }else {
                if (b.bEditarCard.text == "Confirmar") {
                    if (b.tvTitulo.text.isNullOrEmpty()) {
                        Toast.makeText(this, "Introduce un nombre", Toast.LENGTH_SHORT).show()
                    } else {
                        b.tvTitulo.isEnabled=false
                        b.bEditarCard.text="Editar"

                    }
                }
            }


        }

    }
}