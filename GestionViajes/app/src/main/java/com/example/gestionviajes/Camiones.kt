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
import com.example.gestionviajes.databinding.CamionesBinding

/**
 * Actividad que muestra una lista de camiones utilizando RecyclerView.
 * Esta actividad permite ver los detalles de los camiones y añadir nuevos camiones.
 * @author Alex Pineño Sanchez
 */
class Camiones : AppCompatActivity(), OnCardClickListener {

    lateinit var binding: CamionesBinding // Propiedad para acceder a los elementos de la vista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CamionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val rv2 = binding.rvCamiones // Referencia al RecyclerView de camiones

        // Configurar el RecyclerView con los datos de Almacen.camiones
        val adaptador2 = Adaptador(Almacen.camiones, this, this)
        rv2.layoutManager = LinearLayoutManager(this)
        rv2.adapter = adaptador2

        // Acción al hacer clic en el botón "Agregar Camión"
        binding.bAddCamion.setOnClickListener() {
            val i = Intent(this, CrearCamion::class.java)
            startActivity(i)
        }

        // Acción al hacer clic en el botón "Cerrar Sesión"
        binding.bCerrarSesionCamiones.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        // Actualizar el adaptador y notificar los cambios en los datos al volver a la actividad
        val adaptador2 = Adaptador(Almacen.camiones, this, this)
        binding.rvCamiones.adapter = adaptador2
        adaptador2.notifyDataSetChanged()
    }

    /**
     * Maneja la acción al hacer clic en un elemento del RecyclerView.
     * Aquí se maneja la navegación según el elemento seleccionado.
     * @param card El objeto Card seleccionado.
     */
    override fun onCardClick(card: Card) {
        val intent = card.link
        intent.putExtra("objeto", "camion")
        intent.putExtra("marca", card.imagen)
        intent.putExtra("nombre", card.nombre)
        intent.putExtra("detalle", card.detalle)

        startActivity(intent)
    }
}
