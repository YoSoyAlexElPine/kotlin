package AuxiliarDB

import Modelo.Card
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.gestionviajes.AuxiliarDB.AdminConexion
import com.example.gestionviajes.Detalle


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

    fun obtenerCards(contexto: AppCompatActivity):ArrayList<Card>{
        var Cards:ArrayList<Card> = ArrayList(1)

        val admin = AdminConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)

        val bd = admin.readableDatabase
        val fila = bd.rawQuery("select nombre,elo,nacionalidad from personas", null)
        while (fila.moveToNext()) {

            var imagen="@drawable/"+fila.getString(0).toString().toLowerCase()

            // var p:Card = Card(fila.getString(0),fila.getString(1),imagen,fila.getString(2),0,0,"","","",0)
            // Cards.add(p)
        }
        bd.close()
        return Cards //este arrayList lo puedo poner en un adapter de un RecyclerView por ejemplo.
    }
}