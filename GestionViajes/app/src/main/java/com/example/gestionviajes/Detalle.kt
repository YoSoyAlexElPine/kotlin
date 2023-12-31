package com.example.gestionviajes

import AuxiliarDB.Conexion
import Modelo.Almacen
import Modelo.Card
import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.gestionviajes.databinding.DetalleBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.FirebaseFirestore

class Detalle : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val permiso =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted ->

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permiso.launch(Manifest.permission.CALL_PHONE)

        var binding = DetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("nombre").toString()
        val marca = intent.getStringExtra("marca").toString()
        val detalle = intent.getStringExtra("detalle").toString()
        val objeto = intent.getStringExtra("objeto").toString()

        var usuario = ""
        var bd = ""
        var dato = ""

        binding.tvTitulo.isEnabled = false
        binding.tvTitulo.setText(nombre)

        binding.tvDetalle.isEnabled = false
        binding.tvDetalle.setText(detalle)

        val imagen: Int = this.resources.getIdentifier(marca, null, this.packageName)
        if (imagen != 0) {
            val drawable: Drawable = this.resources.getDrawable(imagen)
            binding.tvImagen.setImageDrawable(drawable)
        } else {
            val imagen: Int = this.resources.getIdentifier("@drawable/empleado", null, this.packageName)
            val drawable: Drawable = this.resources.getDrawable(imagen)
            binding.tvImagen.setImageDrawable(drawable)
        }

        lateinit var coleccion: String
        var almacen = Almacen.cards

        if (objeto == "camion") {
            coleccion = "camiones"
            binding.tvTituloDetalle.text = "KM"

            binding.llDetalle4!!.visibility=View.VISIBLE
            binding.llBoton!!.visibility=View.VISIBLE

            bd = "camiones"
            dato = "km"

            almacen = Almacen.camiones
        } else if (objeto == "empleado") {
            coleccion = "usuarios"
            binding.tvTituloDetalle.text = this.getString(R.string.Telefono)

            binding.llDetalle5!!.visibility=View.VISIBLE

            bd = "usuarios"
            dato = "telefono"

            almacen = Almacen.empleados
        } else if (objeto == "viaje") {

            coleccion = "usuarios"
            usuario = intent.getStringExtra("usuario").toString()

            binding.tvTituloDetalle.text = this.getString(R.string.Direccion)
            binding.bEditarCard.visibility = View.GONE
        }
        else if (objeto == "recordatorio") {

            coleccion = "recordatorios"
            usuario = intent.getStringExtra("usuario").toString()

            binding.tvTituloDetalle.visibility = View.GONE
            binding.tvImagen.visibility = View.GONE

            bd = "recordatorios"
            dato = "contenido"

            almacen = Almacen.recordatorios
        }

        binding.bEliminarCard.setOnClickListener() {

            MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                .setTitle(resources.getString(R.string.Eliminar))
                .setMessage(this.getString(R.string.EliminarSeguro))

                .setPositiveButton(resources.getString(R.string.Si)) { dialog, which ->
                    if (objeto == "viaje") {
                        eliminarViajeDeUsuario(usuario, nombre)
                    }

                    if(objeto == "camion"){
                        Conexion.delCard(this,nombre)
                        Almacen.camiones=Conexion.obtenerCamiones(this)
                    }

                    if(objeto == "empleado"){
                        db.collection(coleccion).document(nombre.toString()).delete()
                        almacen.removeIf { it.nombre == nombre }
                    }

                    if(objeto == "recordatorio"){
                        db.collection(coleccion).document(nombre.toString()).delete()
                        almacen.removeIf { it.nombre == nombre }

                    }

                    finish()

                }.setNegativeButton(resources.getString(R.string.No)){ dialog, which ->
                    Toast.makeText(
                        this,
                        this.getString(R.string.NoEliminado),
                        Toast.LENGTH_SHORT
                    ).show()
                }.show()

        }

        binding.bCerrarAsignar.setOnClickListener() {
            if (objeto == "camion") {
                Almacen.camiones = almacen
            } else if (objeto == "empleado") {
                Almacen.empleados = almacen
            }
            finish()
        }

        // Acción al hacer clic en el botón "Editar"
        binding.bEditarCard.setOnClickListener() {
            val id = binding.tvTitulo.text.toString()

            if (binding.bEditarCard.text == this.getString(R.string.Editar)) {
                binding.bEditarCard.text = this.getString(R.string.Confirmar)
                binding.tvDetalle.isEnabled = true
            } else {
                if (binding.bEditarCard.text == this.getString(R.string.Confirmar)) {
                    val nuevoDetalle = binding.tvDetalle.text.toString()

                    if (nuevoDetalle.isNullOrEmpty()) {
                        Toast.makeText(
                            this,
                            this.getString(R.string.IntroduceNombre),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {

                        if (objeto == "empleado") {

                            val documento = db.collection(bd).document(id)

                            documento.update(dato, nuevoDetalle)
                                .addOnSuccessListener {
                                    Modelo.FactoriaCard.sincronizar(this)
                                    binding.tvDetalle.isEnabled = false
                                    binding.bEditarCard.text = this.getString(R.string.Editar)
                                }
                                .addOnFailureListener { exception ->
                                    exception.printStackTrace()
                                }
                        }

                        if (objeto == "recordatorio") {

                            val documento = db.collection(bd).document(id)

                            documento.update(dato, nuevoDetalle)
                                .addOnSuccessListener {
                                    Modelo.FactoriaCard.sincronizar(this)
                                    binding.tvDetalle.isEnabled = false
                                    binding.bEditarCard.text = this.getString(R.string.Editar)
                                }
                                .addOnFailureListener { exception ->
                                    exception.printStackTrace()
                                }
                        }

                        if (objeto == "camion"){

                            val card = Card(nombre,marca, Intent(this,Detalle::class.java),binding.tvDetalle.text.toString())
                            Conexion.modCard(this,nombre,card)
                            binding.tvDetalle.isEnabled = false
                            binding.bEditarCard.text = this.getString(R.string.Editar)
                            Almacen.camiones=Conexion.obtenerCamiones(this)

                        }


                    }
                }
            }
        }

        binding.bLlamar!!.setOnClickListener(){

            val numeroTelefono = binding.tvDetalle.text // Reemplaza con el número de teléfono al que deseas llamar

            val intent = Intent(Intent.ACTION_CALL) // Acción para realizar una llamada
            intent.data = Uri.parse("tel:$numeroTelefono") // Establecer el número de teléfono

            if (intent.resolveActivity(packageManager) != null) {
                // Verificar si hay una aplicación que pueda manejar la acción de llamada
                startActivity(intent)
            } else {
                Toast.makeText(this, "Erro al intentar llamar", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }

        }

        binding.bWasa!!.setOnClickListener(){
            val numeroTelefono = binding.tvDetalle.text // Reemplaza con el número de teléfono de WhatsApp al que deseas enviar el mensaje

            val uri = Uri.parse("https://api.whatsapp.com/send?phone=$numeroTelefono")
            val intent = Intent(Intent.ACTION_VIEW, uri)

            intent.setPackage("com.whatsapp")

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Erro al mandar mensaje por Whatsapp", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }

        }


        binding.bBuscar!!.setOnClickListener(){

            val busqueda = binding.tbBuscar?.text.toString()

            if (!busqueda.isNullOrEmpty()){

                val resultado=Conexion.buscarCard(this, busqueda)


                // Configurar el diálogo
                if(resultado!=null) {

                    val marca = resultado.imagen.substring(10)

                   MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                        .setTitle(resources.getString(R.string.ResultadoBusqueda))
                        .setMessage(this.getString(R.string.Nombre)+" = "+resultado.nombre +"\n"+
                                this.getString(R.string.Marca)+" = "+marca +"\n"+
                                "km = "+resultado.detalle)

                        .setPositiveButton(resources.getString(R.string.Aceptar)) { dialog, which ->

                        }.show()




                } else {

                    MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                        .setTitle(resources.getString(R.string.NoEncontrado))
                        .setMessage(this.getString(R.string.NoEncontradoContenido))
                        .setPositiveButton(resources.getString(R.string.Aceptar)) { dialog, which ->
                        }.show()


                }

            }else{
                Toast.makeText(this,this.getString(R.string.IntroduceNombre),Toast.LENGTH_SHORT).show()
            }



        }

    }

    private fun eliminarViajeDeUsuario(idUsuario: String, nombreViaje: String) {
        val usuarioRef = db.collection("usuarios").document(idUsuario)

        usuarioRef.get().addOnSuccessListener { documentSnapshot ->
            val viajes = documentSnapshot.get("viajes") as? MutableList<Map<String, String>>
                ?: mutableListOf()

            val viajeEncontrado = viajes.find { it["localidad"] == nombreViaje }

            viajeEncontrado?.let {
                viajes.remove(it)

                usuarioRef.update("viajes", viajes)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Viaje eliminado correctamente", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al eliminar el viaje", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                    }
            } ?: run {
                Toast.makeText(this, "No se encontró el viaje", Toast.LENGTH_SHORT).show()
                finish()
            }

        }
    }
}
