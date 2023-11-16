package com.example.gestionviajes

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
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
import com.google.firebase.firestore.DocumentReference
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

        // Encuentra la ImageView en tu diseño (registro.xml)
        val imageView = binding.ivLogoInicio

        // Registra la ImageView para recibir eventos de menú contextual
        registerForContextMenu(imageView)

        // Establece el Listener para la pulsación larga en la ImageView
        imageView.setOnLongClickListener {
            // Muestra el menú contextual
            imageView.showContextMenu()
            true
        }

        // Asignar clic listeners a los botones
        binding.bRegistroRegistrarse.setOnClickListener(){
            var i = Intent(this, RegistarNuevoUsuario::class.java)
            startActivity(i)
        }

        binding.bNotificacion.setOnClickListener(){
            mostrarNotificacion(this, "Título de la Notificación", "Contenido de la Notificación")
        }

        binding.bRegistroEntrar.setOnClickListener {

            val usuariosRef: DocumentReference = db.collection("usuarios").document(binding.tbMail.text.toString())
            var clave=""


            usuariosRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Documento existe, obtener el email
                    val email = documentSnapshot.getString("clave")
                    if (email != null) {
                        clave=email
                        // Hacer algo con el email obtenido
                        println("Email del usuario: $email")
                    } else {
                        // El campo de email está vacío
                        println("El campo de email está vacío para este usuario")
                    }
                } else {
                    // Documento no existe
                    println("El documento con ID $binding.tbMail.text.toString() no existe en la colección 'usuarios'")
                }
            }.addOnFailureListener { e ->
                // Error al obtener el documento
                println("Error al obtener el documento: $e")
            }





// Verificar si los campos de usuario y contraseña no están vacíos
            if (binding.tbMail.text!!.isNotEmpty() && binding.tbContrasena.text!!.isNotEmpty()) {
                val usuario = binding.tbMail.text.toString()
                val contrasena = binding.tbContrasena.text.toString()

                val usuariosRef = db.collection("usuarios").document(usuario)

                usuariosRef.get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // El usuario existe en Firestore, verificar la contraseña
                        val claveAlmacenada = documentSnapshot.getString("clave")

                        if (claveAlmacenada == contrasena) {
                            // La contraseña coincide, inicio de sesión exitoso
                            irHome(usuario, Proveedor.BASIC)
                        } else {
                            // Contraseña incorrecta, mostrar mensaje de error
                            showAlert("Contraseña incorrecta")
                        }
                    } else {
                        // El usuario no existe, mostrar mensaje de error
                        showAlert("El usuario no existe")
                    }
                }.addOnFailureListener { e ->
                    // Error al buscar el usuario en Firestore
                    showAlert("Error al buscar el usuario: $e")
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

        if (requestCode == GOOGLE_SIGN_IN) { // Usar la constante GOOGLE_SIGN_IN
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val cuenta = task.getResult(ApiException::class.java)

                if (cuenta != null) {
                    val credencial = GoogleAuthProvider.getCredential(cuenta.idToken, null)

                    fa.signInWithCredential(credencial)
                        .addOnCompleteListener(this) { signInTask ->
                            if (signInTask.isSuccessful) {
                                irHome(cuenta.email ?: "", Proveedor.GOOGLE)
                            } else {
                                showAlert()
                            }
                        }
                        .addOnFailureListener(this) { exception ->
                            // Manejar errores específicos al iniciar sesión con Google
                            Log.e(TAG, "Error Google: ${exception.message}", exception)
                            showAlert("Error Google: ${exception.message}")
                        }
                }
            } catch(e: ApiException) {
                Log.e(TAG, "Error ApiException Google: ${e.message}", e)
                showAlert("Error Api Google: ${e.message}")
            }
        }
    }

    // Función para mostrar un diálogo de alerta
    private fun showAlert(msg:String = this.getString(R.string.ErrorNombre)){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setPositiveButton(this.getString(R.string.Aceptar),null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    // Función para redirigir a la pantalla de inicio según el usuario y su proveedor
    private fun irHome(usuario: String, proveedor: Proveedor) {
        val camionesCollection = db.collection("usuarios")
        var admin:Boolean=true

        camionesCollection.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.getString("nombre").toString() == usuario){
                        admin = document.getBoolean("admin") ?: false
                    }
                }

                var homeIntent: Intent

                if(admin){
                    homeIntent = Intent(this, Inicio::class.java)
                } else {
                    homeIntent = Intent(this, InicioEmpleado::class.java)
                    homeIntent.putExtra("usuario",usuario)
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




    // Sobrescribe este método para crear el menú contextual
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.setHeaderTitle("Opciones de la imagen")

        // Agrega elementos al menú contextual
        menu.add(0, v.id, 0, "Opción 1")
        menu.add(0, v.id, 0, "Opción 2")
        // Agrega más opciones si es necesario
    }

    // Sobrescribe este método para manejar las selecciones del menú contextual
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.title) {
            "Opción 1" -> {
                // Acción para la Opción 1
                Toast.makeText(this, "Seleccionaste Opción 1", Toast.LENGTH_SHORT).show()
                true
            }
            "Opción 2" -> {
                // Acción para la Opción 2
                Toast.makeText(this, "Seleccionaste Opción 2", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }





}
