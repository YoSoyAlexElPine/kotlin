package com.example.recyclerajedrez

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
        Almacen.personajes = FactoriaListaPersonaje.generaLista(12)
        Log.e("ACSCO", Almacen.personajes.toString())

        miRecyclerView = binding.listaRecycler
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(this)

        var miAdapter = Adaptador.Adaptador(Almacen.personajes, this)

        miRecyclerView.adapter = miAdapter

        binding.bDetalle.setOnClickListener {
            if (Adaptador.Adaptador.seleccionado >= 0) {
                val pe = Almacen.personajes.get(Adaptador.Adaptador.seleccionado)
                Log.e("ACSCO",pe.toString())
                var inte : Intent = Intent(contextoPrincipal, MainActivity2::class.java)
                inte.putExtra("obj", Almacen.personajes.get(Adaptador.Adaptador.seleccionado))
                ContextCompat.startActivity(contextoPrincipal, inte, null)
            }
            else {
                Toast.makeText(this,"Selecciona algo previamente", Toast.LENGTH_SHORT).show()
            }
        }
        contextoPrincipal = this
    }
}