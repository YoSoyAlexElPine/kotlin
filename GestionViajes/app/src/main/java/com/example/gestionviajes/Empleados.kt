package com.example.gestionviajes

import Modelo.Almacen
import Modelo.Card
import Modelo.FactoriaCard
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionviajes.R
import com.example.gestionviajes.adaptador.Adaptador
import com.example.gestionviajes.adaptador.OnCardClickListener
import com.example.gestionviajes.databinding.CamionesBinding
import com.example.gestionviajes.databinding.EmpleadosBinding

class Empleados : AppCompatActivity(), OnCardClickListener {

    lateinit var binding: EmpleadosBinding // Declaración de la propiedad de la vista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmpleadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val rv2 = binding.rvCamiones

        Almacen.empleados = FactoriaCard.empleados(this)
        val adaptador2 = Adaptador(Almacen.empleados, this, this)

        rv2.layoutManager = LinearLayoutManager(this)
        rv2.adapter = adaptador2

        binding.bAddCamion.setOnClickListener() {
            val i = Intent(this, CrearEmpleado::class.java)
            startActivity(i)
        }

        binding.bCerrarSesionCamiones.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        // Actualiza el adaptador y notifica los cambios en los datos
        val adaptador2 = Adaptador(Almacen.empleados, this, this)
        binding.rvCamiones.adapter = adaptador2
        adaptador2.notifyDataSetChanged()
    }

    // Implementa la función de la interfaz para manejar la navegación
    override fun onCardClick(card: Card) {
        // Aquí puedes manejar la navegación según el clic en el RecyclerView
        val intent = card.link
        intent.putExtra("marca", card.imagen)
        intent.putExtra("nombre", card.titulo)

        startActivity(intent)
    }
}