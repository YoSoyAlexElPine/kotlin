package com.example.gestionviajes

import Modelo.Almacen
import Modelo.Card
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionviajes.Adaptador.Adaptador
import com.example.gestionviajes.Adaptador.OnCardClickListener
import com.example.gestionviajes.databinding.EmpleadosBinding
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Actividad para gestionar la visualización y manipulación de la lista de empleados.
 * Permite ver detalles de empleados, añadir nuevos empleados y realizar acciones con la lista de empleados.
 * Implementa la interfaz OnCardClickListener para manejar los clics en los elementos del RecyclerView.
 * @author Alex Pineño Sanchez
 */
class Empleados : AppCompatActivity(), OnCardClickListener {
    lateinit var binding: EmpleadosBinding // Propiedad para acceder a los elementos de la vista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmpleadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Establece la pantalla en modo de pantalla completa
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val recyclerView = binding.rvCamiones

        // Configuración del RecyclerView con la lista de empleados
        val adapter = Adaptador(Almacen.empleados, this, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Acción al hacer clic en el botón "Agregar Empleado"
        binding.bAddCamion.setOnClickListener() {
            val intent = Intent(this, RegistarNuevoUsuario::class.java)
            startActivity(intent)
        }

        // Acción al hacer clic en el botón "Cerrar Sesión"
        binding.bCerrarSesionCamiones.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        // Actualiza el adaptador y notifica los cambios en los datos
        val adapter = Adaptador(Almacen.empleados, this, this)
        binding.rvCamiones.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    // Implementación de la función de la interfaz para manejar la navegación al hacer clic en el RecyclerView
    override fun onCardClick(card: Card) {
        val intent = card.link
        intent.putExtra("objeto", "empleado")
        intent.putExtra("marca", card.imagen)
        intent.putExtra("nombre", card.nombre)
        intent.putExtra("detalle", card.detalle)

        startActivity(intent)
    }
}
