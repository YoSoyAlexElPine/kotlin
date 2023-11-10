package com.example.menujuegos

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.menujuegos.databinding.ActivityMainBinding
import com.example.menujuegos.databinding.InicioBinding
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Inicio : AppCompatActivity() {

    lateinit var binding: InicioBinding
    //Para la autenticación, de cualquier tipo.
    private lateinit var firebaseauth : FirebaseAuth
    val TAG = "ACSCO"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var b= ActivityMainBinding.inflate(layoutInflater)

        setContentView(b.root)

        val nombre=intent.getStringExtra("nombre")
        b.bDados.setOnClickListener(){
            val intent = Intent(this, Dados::class.java)
            intent.putExtra("nombre", nombre)
            startActivity(intent)

        }

        b.bRaya.setOnClickListener(){
            val intent = Intent(this, TresRaya::class.java)
            intent.putExtra("nombre", nombre)
            startActivity(intent)

        }

        b.bSimon.setOnClickListener(){
            val intent = Intent(this, Simon::class.java)
            intent.putExtra("nombre", nombre)
            startActivity(intent)

        }
    }
}













