package ConexionDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
class AdminConexion2(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        Log.e("ACSCO", "Paso por OnCreate del AdminSQLLite")
        db.execSQL("DROP TABLE IF EXISTS personas")
        db.execSQL("create table personas(nombre text primary key, elo text, nacionalidad text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.e("ACSCO", "Paso por OnUpgrade del AdminSQLLite")

        // Realiza cambios en la estructura de la tabla si es necesario
        db.execSQL("DROP TABLE IF EXISTS personas")
        db.execSQL("create table personas(nombre text primary key, elo text, nacionalidad text)")
    }
}