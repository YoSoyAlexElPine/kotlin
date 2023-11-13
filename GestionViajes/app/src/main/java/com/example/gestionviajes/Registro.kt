package com.example.gestionviajes

import Modelo.Almacen
import Modelo.FactoriaCard
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.gestionviajes.databinding.RegistroBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore

class Registro : AppCompatActivity() {
    lateinit var binding: RegistroBinding
    //Para la autenticación, de cualquier tipo.
    private var firebaseauth  = FirebaseAuth.getInstance()
    private val db=FirebaseFirestore.getInstance()
    val TAG = "ACSCO"
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        //try{

        super.onCreate(savedInstanceState)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        //setContentView(R.layout.activity_main)
        binding = RegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bRegistroRegistrarse.setOnClickListener(){
            var i = Intent(this,RegistarNuevoUsuario::class.java)
            startActivity(i)
        }

        binding.bYoutube.setOnClickListener(){

            val url = "https://www.youtube.com"

            // Crear un Intent con la acción ACTION_VIEW y la URL como datos
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            // Abrir la página web
            startActivity(intent)

        }

        binding.bRegistroEntrar.setOnClickListener {
            if (binding.tbMail.text!!.isNotEmpty() && binding.tbContrasena.text!!.isNotEmpty()) {
                firebaseauth.signInWithEmailAndPassword(
                    binding.tbMail.text.toString(),
                    binding.tbContrasena.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        irHome(binding.tbMail.text.toString())  //Esto de los interrogantes es por si está vacío el email.
                    } else {
                        showAlert()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Conexión no establecida", Toast.LENGTH_SHORT).show()
                }

            } else {
                showAlert("Rellene los campos")
            }
        }



            //Para la autenticación, de cualquier tipo.
            firebaseauth = FirebaseAuth.getInstance()




            //------------------ Login Google -------------------
            //------------------------------- -Autenticación Google --------------------------------------------------
            //se hace un signOut por si había algún login antes.
            firebaseauth.signOut()
            //esta variable me conecta con  google. y todo este bloque prepara la ventana de google que se destriba enb el loginInGoogle
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.your_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(this, gso)
            binding.bRegistroGoogle.setOnClickListener {
                loginEnGoogle()
            }

       // }catch (e:Exception ){
        //    Log.e(TAG, e.printStackTrace().toString())
        //}
    }

    //******************************* Para el login con Google ******************************
    private lateinit var googleSignInClient: GoogleSignInClient

    //con este launcher, abro la ventana que me lelva a la validacion de Google.
    private val launcherVentanaGoogle =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        //si la ventana va bien, se accede a las propiedades que trae la propia ventana q llamamos y recogemos en result.
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            manejarResultados(task)
        }
    }

    //es como una muñeca rusa, vamos desgranando, de la ventana a task y de task a los datos concretos que me da google.
    private fun manejarResultados(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                actualizarUI(account)
            }
        }
        else {
            Toast.makeText(this,task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    //esta funcion actualiza o repinta la interfaz de usuario UI.
    private fun actualizarUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        //pido un token, y con ese token, si todo va bien obtengo la info.
        firebaseauth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                //hacer account. y ver otras propiedades interesantes.
                irHome(binding.tbMail.text.toString())
            }
            else {
                Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


    //--------
    private fun loginEnGoogle(){
        //este método es nuestro.
        val signInClient = googleSignInClient.signInIntent
        launcherVentanaGoogle.launch(signInClient)
        //milauncherVentanaGoogle.launch(signInClient)
    }

    //************************************** Funciones auxiliares **************************************
    //*********************************************************************************
    private fun showAlert(msg:String = "Se ha producido un error autenticando al usuario"){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    //*********************************************************************************
    private fun irHome(mail:String) {

        val camionesCollection = db.collection("usuarios")
        var admin:Boolean=true

        camionesCollection.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {

                    if (document.getString("mail").toString()==mail){
                        admin=document.getString("admin").toBoolean()
                    }

                }


            }
            .addOnFailureListener { exception ->
                // Manejar errores al obtener documentos
                exception.printStackTrace()
            }

        var homeIntent:Intent

        if(admin){
            homeIntent = Intent(this, Inicio::class.java)
        } else {
            homeIntent = Intent(this, InicioEmpleado::class.java)
        }

        startActivity(homeIntent)
    }





}