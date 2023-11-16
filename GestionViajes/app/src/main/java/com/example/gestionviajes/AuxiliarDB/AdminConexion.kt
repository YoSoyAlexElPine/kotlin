package com.example.gestionviajes.AuxiliarDB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

/**
 * Clase que gestiona la creación y actualización de la base de datos SQLite.
 *
 * @param context Contexto de la aplicación.
 * @param name Nombre de la base de datos.
 * @param factory No se utiliza, dejar como null.
 * @param version Número de versión de la base de datos.
 * @constructor Crea un AdminConexion para la gestión de la base de datos SQLite.
 * @author Alex Pineño Sanchez
 */
class AdminConexion(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    /**
     * Método llamado cuando se crea la base de datos por primera vez.
     *
     * @param db Base de datos SQLite.
     */
    override fun onCreate(db: SQLiteDatabase) {
        Log.e("APS", "Paso por OnCreate del AdminSQLLite")

        // Crea una tabla 'empleados' con las columnas 'nombre' y 'telefono'
        db.execSQL("create table camiones(nombre text primary key, marca text,km text)")
    }

    /**
     * Método llamado cuando se actualiza la versión de la base de datos.
     *
     * @param db Base de datos SQLite.
     * @param oldVersion Número de la versión anterior.
     * @param newVersion Número de la nueva versión.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.e("APS", "Paso por OnUpgrade del AdminSQLLite")

        // Actualiza la tabla 'empleados' si es necesario en una nueva versión
        db.execSQL("create table camiones(nombre text primary key, marca text,km text)")
    }
}
