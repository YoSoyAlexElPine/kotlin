package AuxiliarDB

import ConexionDB.AdminConexion2
import Modelo.Ajedrecista
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity


object Conexion {

    private  var DATABASE_NAME = "administracion4.db3"
    private  var DATABASE_VERSION = 1


    fun cambiarBD(nacionalidadBD:String){
        this.DATABASE_NAME = nacionalidadBD
    }

    fun addAjedrecista(contexto: AppCompatActivity, p: Ajedrecista):Long{
        val admin = AdminConexion2(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase //habilito la BBDD para escribir en ella, tambié deja leer.
        val registro = ContentValues() //objeto de kotlin, contenido de valores, un Map. Si haceis ctrl+clic lo veis.
        registro.put("nombre", p.nombre)
        registro.put("elo",p.elo)
        registro.put("nacionalidad", p.nacionalidad)
        val codigo = bd.insert("personas", null, registro)
        bd.close()
        return codigo
    }

    fun delAjedrecista(contexto: AppCompatActivity, nombre: String):Int{
        val admin = AdminConexion2(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase

        val cant = bd.delete("personas", "nombre=?", arrayOf(nombre))
        bd.close()
        return cant
    }

    fun modAjedrecista(contexto: AppCompatActivity, nombre:String, p:Ajedrecista):Int {
        val admin = AdminConexion2(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("elo",p.elo.toString())
        registro.put("nacionalidad", p.nacionalidad)


        val cant = bd.update("personas", registro, "nombre=?", arrayOf(nombre.toString()))

        bd.close()
        return cant
    }

    fun buscarAjedrecista(contexto: AppCompatActivity, nombre:String):Ajedrecista? {
        var p:Ajedrecista? = null //si no encuentra ninguno vendrá null, por eso la ? y también en la devolución de la función.
        val admin = AdminConexion2(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase

        val fila =bd.rawQuery(
            "SELECT elo,nacionalidad FROM personas WHERE nombre=?",
            arrayOf(nombre.toString())
        )
        //en fila viene un CURSOR, que está justo antes del primero por eso lo ponemos en el primero en la siguiente línea
        if (fila.moveToFirst()) {//si no hay datos el moveToFirst, devuelve false, si hay devuelve true.
            p = Ajedrecista(nombre,fila.getString(0),nombre,fila.getString(1),0,0,"0","","",0)
        }
        bd.close()
        return p
    }

    fun obtenerAjedrecistas(contexto: AppCompatActivity):ArrayList<Ajedrecista>{
        var Ajedrecistas:ArrayList<Ajedrecista> = ArrayList(1)

        val admin = AdminConexion2(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)

        val bd = admin.readableDatabase
        val fila = bd.rawQuery("select nombre,elo,nacionalidad from personas", null)
        while (fila.moveToNext()) {

            var imagen="@drawable/"+fila.getString(0).toString().toLowerCase()

            var p:Ajedrecista = Ajedrecista(fila.getString(0),fila.getString(1),imagen,fila.getString(2),0,0,"","","",0)
            Ajedrecistas.add(p)
        }
        bd.close()
        return Ajedrecistas //este arrayList lo puedo poner en un adapter de un RecyclerView por ejemplo.
    }
}