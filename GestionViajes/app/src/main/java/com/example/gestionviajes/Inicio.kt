package com.example.gestionviajes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionviajes.adaptador.Adaptador
import com.example.gestionviajes.databinding.InicioBinding
import com.google.firebase.auth.FirebaseAuth

class Inicio : AppCompatActivity() {
    lateinit var binding: InicioBinding
    private lateinit var firebaseauth : FirebaseAuth
    val TAG = "ACSCO"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        binding = InicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val rv=binding.rvInicio
        val adaptador=Adaptador()

        adaptador.imagenes= arrayOf(R.drawable.camion,R.drawable.tarea)
        adaptador.titulos = arrayOf("Camiones","Asignar tarea")

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
}