package com.example.recyclerajedrez

import Adaptador.Adaptador
import Modelo.Almacen
import Modelo.FactoriaListaPersonaje
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerajedrez.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var miRecyclerView : RecyclerView
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var contextoPrincipal: Context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Almacen.ajedrecistas = FactoriaListaPersonaje.generaLista(12)

        miRecyclerView = binding.listaRecycler
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)

        var miAdapter = Adaptador(Almacen.ajedrecistas, this)

        miRecyclerView.adapter = miAdapter

        binding.bDetalle.setOnClickListener {

            if (Adaptador.seleccionado >= 0) {

                var segundaPantalla = Intent(this, Pantalla2::class.java)

                segundaPantalla.putExtra("ajedrecista", Adaptador.ajedrecistaSeleccionado)

                ContextCompat.startActivity(contextoPrincipal, segundaPantalla, null)

                startActivity(segundaPantalla)


            }
            else {
                Toast.makeText(this,"Selecciona algo previamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}