package com.example.recyclerajedrez

import Adaptador.Adaptador
import AuxiliarDB.Conexion
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

        try {
            Log.e("ACSCO","paso create")
            contextoPrincipal=this

            super.onCreate(savedInstanceState)

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            Almacen.ajedrecistas = Conexion.obtenerAjedrecistas(this)


            miRecyclerView = binding.listaRecycler
            miRecyclerView.setHasFixedSize(true)
            miRecyclerView.layoutManager = LinearLayoutManager(this)

            var miAdapter = Adaptador(Almacen.ajedrecistas, this)

            miRecyclerView.adapter = miAdapter

            binding.bDetalle.setOnClickListener {

                if (Adaptador.seleccionado >= 0) {

                    val pe = Almacen.ajedrecistas.get(Adaptador.seleccionado)
                    Log.e("ACSCO",pe.toString())
                    var inte : Intent = Intent(contextoPrincipal, Pantalla2::class.java)
                    inte.putExtra("ajedrecista",Almacen.ajedrecistas.get(Adaptador.seleccionado))
                    ContextCompat.startActivity(contextoPrincipal, inte, null)

                } else {
                    Toast.makeText(this, "Selecciona algo previamente", Toast.LENGTH_SHORT).show()
                }
            }

            binding.bMantenimiento.setOnClickListener(){

                for (a in Conexion.obtenerAjedrecistas(this)){
                    Conexion.delAjedrecista(this, a.nombre)
                }

                for (a in Almacen.ajedrecistas){
                    Conexion.addAjedrecista(this,a)
                }

                var inte:Intent = Intent(contextoPrincipal,Mantenimiento::class.java)
                ContextCompat.startActivity(contextoPrincipal,inte,null)
            }

            binding.bReiniciar.setOnClickListener(){

                Almacen.ajedrecistas=FactoriaListaPersonaje.generaLista(12)

                for (a in Almacen.ajedrecistas){
                    Conexion.addAjedrecista(this,a)
                }

                miRecyclerView = binding.listaRecycler
                miRecyclerView.setHasFixedSize(true)
                miRecyclerView.layoutManager = LinearLayoutManager(this)

                var miAdapter = Adaptador(Almacen.ajedrecistas, this)

                miRecyclerView.adapter = miAdapter


            }


        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}