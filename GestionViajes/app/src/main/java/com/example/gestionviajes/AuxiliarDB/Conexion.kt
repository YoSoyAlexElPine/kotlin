package AuxiliarDB

import Modelo.Card
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionviajes.AuxiliarDB.AdminConexion
import com.example.gestionviajes.Detalle
import java.util.Locale


object Conexion {

    private  var DATABASE_NAME = "administracion.db3"
    private  var DATABASE_VERSION = 1


    fun cambiarBD(nacionalidadBD:String){
        this.DATABASE_NAME = nacionalidadBD
    }

    fun addCard(contexto: AppCompatActivity, p: Card):Long{
        val admin = AdminConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase //habilito la BBDD para escribir en ella, tambié deja leer.
        val registro = ContentValues() //objeto de kotlin, contenido de valores, un Map. Si haceis ctrl+clic lo veis.
        registro.put("nombre", p.titulo)
        registro.put("nacionalidad", p.imagen)
        val codigo = bd.insert("personas", null, registro)
        bd.close()
        return codigo
    }

    fun delCard(contexto: AppCompatActivity, nombre: String):Int{
        val admin = AdminConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase

        val cant = bd.delete("personas", "nombre=?", arrayOf(nombre))
        bd.close()
        return cant
    }

    fun modCard(contexto: AppCompatActivity, nombre:String, p:Card):Int {
        val admin = AdminConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        val registro = ContentValues()


        val cant = bd.update("personas", registro, "nombre=?", arrayOf(nombre.toString()))

        bd.close()
        return cant
    }

    fun buscarCard(contexto: AppCompatActivity, nombre:String,objeto:String):Card? {
        var p:Card? = null
        val admin = AdminConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase

        var fila =bd.rawQuery(
            "SELECT * FROM empleados WHERE nombre=?",
            arrayOf(nombre)
        )

        if (objeto=="empleado"){
            fila =bd.rawQuery(
                "SELECT telefono FROM empleados WHERE nombre=?",
                arrayOf(nombre)
            )
        }
        if (objeto=="camion"){
            fila =bd.rawQuery(
                "SELECT marca,km FROM camiones WHERE nombre=?",
                arrayOf(nombre)
            )
        }

        if (fila.moveToFirst()) {

            if (objeto=="empleado"){
                p = Card(nombre,"drawable/empleado", Intent(contexto,Detalle::class.java),fila.getString(0))
            }
            if (objeto=="camion"){
                p = Card(nombre,"drawable/"+fila.getString(0), Intent(contexto,Detalle::class.java),fila.getString(1))
            }

        }
        bd.close()
        return p
    }

    fun obtenerEmpleados(contexto: AppCompatActivity):MutableList<Card>{
        val Cards:MutableList<Card> = mutableListOf()

        val admin = AdminConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)

        val bd = admin.readableDatabase
        val fila = bd.rawQuery("select nombre,telefono from empleados", null)
        while (fila.moveToNext()) {

            val imagen="@drawable/empleado"

            val p = Card(fila.getString(0),imagen,Intent(contexto,Detalle::class.java),fila.getString(1))
            Cards.add(p)
        }
        bd.close()
        return Cards
    }

    fun obtenerCamiones(contexto: AppCompatActivity):MutableList<Card>{
        val Cards:MutableList<Card> = mutableListOf()

        val admin = AdminConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)

        val bd = admin.readableDatabase
        val fila = bd.rawQuery("select nombre,marca,km from camiones", null)
        while (fila.moveToNext()) {

            val imagen="@drawable/"+fila.getString(1).toString().toLowerCase(Locale.ROOT)

            val p = Card(fila.getString(0),imagen,Intent(contexto,Detalle::class.java),fila.getString(2))
            Cards.add(p)
        }
        bd.close()
        return Cards
    }
}