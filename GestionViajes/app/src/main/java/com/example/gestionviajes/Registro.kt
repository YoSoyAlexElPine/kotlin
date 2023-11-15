package com.example.gestionviajes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.gestionviajes.Notificacion.mostrarNotificacion
import com.example.gestionviajes.databinding.RegistroBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Clase Registro para manejar el registro e inicio de sesión de usuarios.
 * @author Alex Pineño Sanchez
 */
class Registro : AppCompatActivity() {
    lateinit var binding: RegistroBinding

    // Constante para el código de inicio de sesión de Google
    val GOOGLE_SIGN_IN = 100

    // Instancias de FirebaseAuth y FirebaseFirestore
    private var fa = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    val TAG = "ACSCO"

    // Función onCreate, se llama al crear la actividad
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración de la ventana
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Inflar y establecer la vista usando el archivo de diseño "RegistroBinding"
        binding = RegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Asignar clic listeners a los botones
        binding.bRegistroRegistrarse.setOnClickListener(){
            var i = Intent(this, RegistarNuevoUsuario::class.java)
            startActivity(i)
        }

        binding.bNotificacion.setOnClickListener(){
            mostrarNotificacion(this, "Título de la Notificación", "Contenido de la Notificación")
        }

        binding.bRegistroEntrar.setOnClickListener {
            // Verificar si los campos de correo y contraseña no están vacíos
            if (binding.tbMail.text!!.isNotEmpty() && binding.tbContrasena.text!!.isNotEmpty()) {
                // Iniciar sesión con correo y contraseña
                fa.signInWithEmailAndPassword(
                    binding.tbMail.text.toString(),
                    binding.tbContrasena.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Ir a la pantalla de inicio según el usuario y su proveedor
                        irHome(binding.tbMail.text.toString(), Proveedor.BASIC)
                    } else {
                        showAlert() // Mostrar diálogo de alerta en caso de error
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Conexión no establecida", Toast.LENGTH_SHORT).show()
                }
            } else {
                showAlert("Rellene los campos") // Mostrar diálogo de alerta para campos vacíos
            }
        }

        // Obtener extras del intent para guardar datos en SharedPreferences
        val bundle = intent.extras
        val mail = bundle?.getString("mail")
        val provider = bundle?.getString("provider")

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("main", mail)
        prefs.putString("provider", provider)
        prefs.apply()

        // Configurar clic listener para el botón de registro con Google
        binding.bRegistroGoogle.setOnClickListener {
            // Configurar opciones para el inicio de sesión con Google
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.your_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this,googleConf)
            googleClient.signOut() // Cerrar sesión de Google

            // Iniciar la actividad de inicio de sesión con Google y esperar el resultado
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }

        // Verificar si existe una sesión guardada al iniciar la actividad
        session()
    }

    // Función para manejar el resultado del inicio de sesión con Google
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val cuenta = task.getResult(ApiException::class.java)

                if (cuenta != null) {
                    val credencial = GoogleAuthProvider.getCredential(cuenta.idToken, null)

                    FirebaseAuth.getInstance().signInWithCredential(credencial)
                        .addOnCompleteListener() {
                            if (it.isSuccessful) {
                                irHome(cuenta.email ?: "", Proveedor.GOOGLE)
                            } else {
                                showAlert()
                            }
                        }
                }
            } catch(e: ApiException){
                Log.e("APS",e.toString())
                showAlert()
            }
        }
    }

    // Función para mostrar un diálogo de alerta
    private fun showAlert(msg:String = "Se ha producido un error autenticando al usuario"){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    // Función para redirigir a la pantalla de inicio según el usuario y su proveedor
    private fun irHome(mail: String, proveedor: Proveedor) {
        val camionesCollection = db.collection("usuarios")
        var admin:Boolean=true

        camionesCollection.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.getString("mail").toString() == mail){
                        admin = document.getBoolean("admin") ?: false
                    }
                }

                var homeIntent: Intent

                if(admin){
                    homeIntent = Intent(this, Inicio::class.java)
                } else {
                    homeIntent = Intent(this, InicioEmpleado::class.java)
                }

                startActivity(homeIntent)
            }
            .addOnFailureListener { exception ->
                // Manejar errores al obtener documentos
                exception.printStackTrace()
            }
    }

    // Función para verificar si existe una sesión guardada en SharedPreferences
    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)

        val mail = prefs.getString("mail",null)
        val provider = prefs.getString("provider",null)

        if (mail != null && provider != null){
            irHome(mail, Proveedor.valueOf(provider))
        }
    }
}
