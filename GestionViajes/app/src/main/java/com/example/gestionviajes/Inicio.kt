package com.example.gestionviajes

import Modelo.Almacen
import Modelo.Card
import Modelo.FactoriaCard
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionviajes.adaptador.Adaptador
import com.example.gestionviajes.adaptador.OnCardClickListener
import com.example.gestionviajes.databinding.InicioBinding
import com.google.firebase.auth.FirebaseAuth

class Inicio : AppCompatActivity(), OnCardClickListener {
    lateinit var binding: InicioBinding
    private lateinit var firebaseauth : FirebaseAuth
    val TAG = "APS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = InicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val rv=binding.rvInicio

        FactoriaCard.sincronizar(this)

        Almacen.cards=FactoriaCard.inicioAdmin(this)
        val adaptador=Adaptador(Almacen.cards,this,this)

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter=adaptador


        //Para la autenticación, de cualquier tipo.
        firebaseauth = FirebaseAuth.getInstance()

        binding.bCerrarSesion.setOnClickListener {
            Log.e(TAG, firebaseauth.currentUser.toString())

            firebaseauth.signOut()
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