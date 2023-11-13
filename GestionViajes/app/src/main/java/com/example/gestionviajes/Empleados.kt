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
import com.example.gestionviajes.databinding.EmpleadosBinding
import com.google.firebase.firestore.FirebaseFirestore

class Empleados : AppCompatActivity(), OnCardClickListener {
    private val db= FirebaseFirestore.getInstance()
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

        val camionesCollection = db.collection("empleados")

        camionesCollection.get()
            .addOnSuccessListener { documents ->
                Almacen.empleados.clear()
                for (document in documents) {
                    // Convierte cada documento en un objeto Card y agrégalo a Almacen.camiones
                    val card = FactoriaCard.documentoACardEmpleado(this,document)
                    Almacen.empleados.add(card)
                }

                // Configura el RecyclerView con los datos actualizados
                val adaptador2 = Adaptador(Almacen.empleados, this, this)
                rv2.layoutManager = LinearLayoutManager(this)
                rv2.adapter = adaptador2
            }
            .addOnFailureListener { exception ->
                // Manejar errores al obtener documentos
                exception.printStackTrace()
            }

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