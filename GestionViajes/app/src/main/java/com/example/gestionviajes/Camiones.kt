package com.example.gestionviajes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionviajes.adaptador.Adaptador
import com.example.gestionviajes.databinding.CamionesBinding

class Camiones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        var binding = CamionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val rv=binding.rvCamiones
        val adaptador= Adaptador(this)

        adaptador.imagenes= arrayOf(R.drawable.camion,R.drawable.tarea)
        adaptador.titulos = arrayOf("Camiones","Asignar tarea")

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter=adaptador


        binding.bAddCamion.setOnClickListener(){
            var i=Intent(this,CrearCamion::class.java)
            startActivity(i)
        }

        binding.bCerrarSesionCamiones.setOnClickListener {
            finish()
        }
    }
}