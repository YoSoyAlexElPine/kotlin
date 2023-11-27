package com.example.gestionviajes

import Modelo.Almacen
import Modelo.Card
import Modelo.FactoriaCard
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gestionviajes.Adaptador.Adaptador
import com.example.gestionviajes.Adaptador.OnCardClickListener
import com.example.gestionviajes.databinding.InicioBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.web -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://transgarciavillaracosl.negocio.site")))
            R.id.info -> {

                MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                    .setTitle(resources.getString(R.string.AcercaDe))
                    .setMessage(resources.getString(R.string.AcercaDeContenido)+"\n\n"+resources.getString(R.string.AcercaDeContenido2)+"\n\n"+resources.getString(R.string.AcercaDeContenido3)+"\n\n")

                    .setPositiveButton(resources.getString(R.string.Aceptar)) { dialog, which ->
                        // Respond to positive button press
                    }
                    .show()
            }

            R.id.email -> startActivity(Intent(this, EnviarMail::class.java))

        }
        return super.onOptionsItemSelected(item)
    }

    // Implementa la función de la interfaz para manejar la navegación
    override fun onCardClick(card: Card) {
        // Aquí puedes manejar la navegación según el clic en el RecyclerView
        val intent = card.link
        startActivity(intent)
    }
}
