package com.example.recyclerajedrez

import AuxiliarDB.Conexion
import Modelo.Ajedrecista
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Mantenimiento : AppCompatActivity() {
    lateinit var edNombre: EditText
    lateinit var edElo: EditText
    lateinit var botonAdd: Button
    lateinit var botonBuscar: Button
    lateinit var botonBorrar: Button
    lateinit var botonEditar: Button
    lateinit var txtListdo: TextView
    lateinit var edNacionalidad:EditText
    lateinit var botonVolver:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("ACSCO","Paso por OnCreate ")
        setContentView(R.layout.activity_main3)
        edNombre = findViewById(R.id.tb_nombre)
        edNombre.requestFocus()
        edElo = findViewById(R.id.tb_elo)
        edNacionalidad=findViewById(R.id.tb_nacionalidad)
        botonAdd = findViewById(R.id.b_anadir)
        botonBuscar = findViewById(R.id.b_buscar)
        botonBorrar = findViewById(R.id.b_borrar)
        botonEditar = findViewById(R.id.b_editar)
        txtListdo = findViewById(R.id.tv_listado)
        botonVolver =findViewById(R.id.b_volverMantenimiento)


        botonVolver.setOnClickListener(){
            var init=Intent(this,MainActivity::class.java)
            startActivity(init)
        }

    }


    fun addAjedrecista(view: View) {
        if (edNombre.text.toString().trim().isEmpty() || edElo.text.toString().trim().isEmpty()
            || edNacionalidad.text.toString().trim().isEmpty()){
            val builder = AlertDialog.Builder(this)
            with(builder)
            {
                setTitle("Faltan campos")
                setMessage("Rellena tods los campos antes de agregar")
                setPositiveButton("Aceptar", DialogInterface.OnClickListener(function = positiveButtonClick))
                show()
            }
        }
        else {

            var imagen="@drawable/"+"nuevo"

            var pers: Ajedrecista = Ajedrecista(
                edNombre.getText().toString(),
                edElo.getText().toString(),
                imagen,
                edNacionalidad.getText().toString(),
                0,0,"null","null","null",0
            )
            var codigo= Conexion.addAjedrecista(this, pers)
            edNacionalidad.setText("")
            edNombre.setText("")
            edElo.setText("")
            edNombre.requestFocus()
            //la L es por ser un Long lo que trae codigo.
            if(codigo!=-1L) {
                val builder = AlertDialog.Builder(this)
                with(builder)
                {
                    setTitle("Ajedrecista introducido")
                    setMessage("Ya puedes ver y usar tu ajedrecista")
                    setPositiveButton("Aceptar", DialogInterface.OnClickListener(function = positiveButtonClick))
                    show()
                }
                listarAjedrecistas(view)
            }
            else{

                val builder = AlertDialog.Builder(this)
                with(builder)
                {
                    setTitle("Ya existe")
                    setMessage("No puedes agregar otro ajedrecista que ya existe")
                    setPositiveButton("Aceptar", DialogInterface.OnClickListener(function = positiveButtonClick))
                    show()
                }
        }
    }
    }

    fun delAjedrecista(view: View) {

        val contexto=this

        var cant = Conexion.buscarAjedrecista(this, edNombre.text.toString())
        edNombre.setText("")
        edNacionalidad.setText("")
        edElo.setText("")
        if (cant != null) {
            val builder = AlertDialog.Builder(this)

            with(builder)
            {
                setTitle("Eliminar ajedrecista")
                setMessage("¿Estas seguro que quieres eliminar?")
                //Otra forma es definir directamente aquí lo que se hace cuando se pulse.
                setPositiveButton("Si", DialogInterface.OnClickListener(function = { dialog: DialogInterface, which: Int ->
                    Conexion.delAjedrecista(contexto,cant.nombre)
                }))
                setNegativeButton("No", ({ dialog: DialogInterface, which: Int ->
                    Toast.makeText(applicationContext,
                        "Has pulsado no", Toast.LENGTH_SHORT).show()
                }))
                show() //builder.show()
            }
        }
        else
            Toast.makeText(this, "No existe ese ajedrecista", Toast.LENGTH_SHORT).show()

    }

    fun modAjedrecista(view: View) {
        if (edNacionalidad.text.toString().trim().isEmpty()|| edNombre.text.toString().trim().isEmpty()
            || edElo.text.toString().trim().isEmpty()){
            val builder = AlertDialog.Builder(this)
            with(builder)
            {
                setTitle("Faltan campos")
                setMessage("Rellena tods los campos antes de agregar")
                setPositiveButton("Aceptar", DialogInterface.OnClickListener(function = positiveButtonClick))
                show()
            }
        }
        else {
            var pers: Ajedrecista = Ajedrecista(
                edNombre.getText().toString(),
                edElo.getText().toString(),
                edNombre.getText().toString(),
                edNacionalidad.getText().toString(),
                0,0,"null","null","null",0
            )
            var cant = Conexion.modAjedrecista(this, edNombre.text.toString(), pers)
            if (cant == 1)
                Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No existe una Ajedrecista con ese DNI", Toast.LENGTH_SHORT).show()
        }
        listarAjedrecistas(view)
    }

    fun buscarAjedrecista(view: View) {
        var p:Ajedrecista? = null
        p = Conexion.buscarAjedrecista(this, edNombre.text.toString())
        if (p!=null) {
            edNombre.setText(p.nombre)
            edElo.setText(p.elo)
            edNacionalidad.setText(p.nacionalidad)
        } else {
            Toast.makeText(this, "No existe ese ajedrecista", Toast.LENGTH_SHORT).show()
        }

    }

    fun listarAjedrecistas(view: View) {
        var listado:ArrayList<Ajedrecista> = Conexion.obtenerAjedrecistas(this)

        txtListdo.setText("")

        if (listado.size==0) {
            Toast.makeText(this, "No existen datos en la tabla", Toast.LENGTH_SHORT).show()
        }
        else {
            for(p in listado){
                var cadena = p.nombre + ", " + p.elo + ", " + p.nacionalidad + "\r\n"
                txtListdo.append(cadena)
            }
        }
    }


    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
    }


}