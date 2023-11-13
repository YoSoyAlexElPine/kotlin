package com.example.gestionviajes

import Modelo.Almacen
import Modelo.Card
import Modelo.FactoriaCard
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionviajes.adaptador.Adaptador
import com.example.gestionviajes.adaptador.OnCardClickListener
import com.example.gestionviajes.databinding.CamionesBinding
import com.google.firebase.firestore.FirebaseFirestore

class Camiones : AppCompatActivity(), OnCardClickListener {

    val db = FirebaseFirestore.getInstance()
    lateinit var binding: CamionesBinding // Declaración de la propiedad de la vista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CamionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val rv2 = binding.rvCamiones

        // Obtén todos los documentos de la colección "camiones" en Firestore

        val camionesCollection = db.collection("camiones")

        camionesCollection.get()
            .addOnSuccessListener { documents ->
                Almacen.camiones.clear()
                for (document in documents) {
                    // Convierte cada documento en un objeto Card y agrégalo a Almacen.camiones
                    val card = FactoriaCard.documentoACardCamion(this,document)
                    Almacen.camiones.add(card)
                }

                // Configura el RecyclerView con los datos actualizados
                val adaptador2 = Adaptador(Almacen.camiones, this, this)
                rv2.layoutManager = LinearLayoutManager(this)
                rv2.adapter = adaptador2
            }
            .addOnFailureListener { exception ->
                // Manejar errores al obtener documentos
                exception.printStackTrace()
            }

        binding.bAddCamion.setOnClickListener() {
            val i = Intent(this, CrearCamion::class.java)
            startActivity(i)
        }

        binding.bCerrarSesionCamiones.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        // Actualiza el adaptador y notifica los cambios en los datos
        val adaptador2 = Adaptador(Almacen.camiones, this, this)
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
