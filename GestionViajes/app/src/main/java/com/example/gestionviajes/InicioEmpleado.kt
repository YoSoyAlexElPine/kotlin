package com.example.gestionviajes

import Modelo.Card
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionviajes.Adaptador.Adaptador
import com.example.gestionviajes.Adaptador.OnCardClickListener
import com.example.gestionviajes.databinding.InicioEmpleadoBinding
import com.google.firebase.firestore.FirebaseFirestore

class InicioEmpleado : AppCompatActivity(), OnCardClickListener {

    private lateinit var binding: InicioEmpleadoBinding
    private lateinit var adaptador: Adaptador
    private lateinit var usuario: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InicioEmpleadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuario = intent.getStringExtra("usuario").toString()
        adaptador = Adaptador(mutableListOf(), this, this)
        binding.rvInicioEmpleado.adapter = adaptador
        binding.rvInicioEmpleado.layoutManager = LinearLayoutManager(this)

        cargarViajesAsignados(usuario)
    }

    private fun cargarViajesAsignados(idUsuario: String?) {
        if (idUsuario != null) {
            val db = FirebaseFirestore.getInstance()
            val usuarioRef = db.collection("usuarios").document(idUsuario)

            usuarioRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val viajes = documentSnapshot.get("viajes") as? List<Map<String, String>>

                    if (!viajes.isNullOrEmpty()) {
                        val cards = viajes.map { viaje ->
                            Card(
                                viaje["localidad"] ?: "",
                                "@drawable/fantasma", // Reemplaza con la imagen correspondiente
                                Intent(this,Detalle::class.java), // Reemplaza con el Intent correspondiente
                                viaje["direccion"] ?: ""
                            )
                        }
                        adaptador.Cards.clear()
                        adaptador.Cards.addAll(cards)
                        adaptador.notifyDataSetChanged()
                    }
                }
            }.addOnFailureListener { exception ->
                // Manejar errores al obtener los viajes asignados
                // Puedes mostrar un Toast o algún mensaje de error aquí
            }
        }
    }
    override fun onResume() {
        super.onResume()

        cargarViajesAsignados(usuario)
    }
    override fun onCardClick(card: Card) {
        // Acción al hacer clic en un elemento del RecyclerView
        val intent = card.link
        intent.putExtra("objeto", "viaje")
        intent.putExtra("marca", card.imagen)
        intent.putExtra("nombre", card.nombre)
        intent.putExtra("detalle", card.detalle)
        intent.putExtra("usuario", usuario)

        startActivity(intent)
    }
}
