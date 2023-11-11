package com.example.gestionviajes

import Modelo.Almacen
import Modelo.Card
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gestionviajes.databinding.AsignarTareaBinding
import com.example.gestionviajes.databinding.CrearCamionBinding

class CrearCamion : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var b = CrearCamionBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.bCrearCamion.setOnClickListener(){
            if (!b.tbChofer.text.isNullOrEmpty() && !b.tbMarca.text.isNullOrEmpty()&& !b.tbNombre.text.isNullOrEmpty()){

                var marca=b.tbMarca.text.toString().trim().toLowerCase()

                try {

                    Almacen.camiones.add(
                        Card(
                            b.tbNombre.text.toString(),
                            "@drawable/" + marca,
                            Intent()
                        )
                    )
                }catch (e:Exception){
                    Almacen.camiones.add(
                        Card(
                            b.tbNombre.text.toString(),
                            "@drawable/camion2",
                            Intent()
                        )
                    )
                }
                }
        }

        b.bCerrarAsignar.setOnClickListener {
            finish()
        }

    }
}