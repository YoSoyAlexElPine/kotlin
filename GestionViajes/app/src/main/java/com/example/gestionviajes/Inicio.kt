package com.example.gestionviajes

import Modelo.Almacen
import Modelo.Card
import Modelo.FactoriaCard
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionviajes.Adaptador.Adaptador
import com.example.gestionviajes.Adaptador.OnCardClickListener
import com.example.gestionviajes.databinding.InicioBinding
import com.google.firebase.auth.FirebaseAuth

/**
 * Actividad principal que muestra la interfaz de inicio de la aplicación.
 * Gestiona la visualización de tarjetas en el RecyclerView y la navegación al hacer clic en ellas.
 * @author Alex Pineño Sanchez
 */
class Inicio : AppCompatActivity(), OnCardClickListener {
    lateinit var binding: InicioBinding
    private lateinit var firebaseauth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar y establecer la vista de la actividad
        binding = InicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Establecer la pantalla en modo de pantalla completa
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val recyclerView = binding.rvInicio

        // Sincronizar datos de Firestore y actualizar la lista de tarjetas en Almacen.cards
        FactoriaCard.sincronizar(this)
        Almacen.cards = FactoriaCard.inicioAdmin(this)
        val adapter = Adaptador(Almacen.cards, this, this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Para la autenticación, de cualquier tipo.
        firebaseauth = FirebaseAuth.getInstance()

        binding.bCerrarSesion.setOnClickListener {

            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            finish()
        }
    }

    // Implementa la función de la interfaz para manejar la navegación
    override fun onCardClick(card: Card) {
        // Aquí puedes manejar la navegación según el clic en el RecyclerView
        val intent = card.link
        startActivity(intent)
    }
}
