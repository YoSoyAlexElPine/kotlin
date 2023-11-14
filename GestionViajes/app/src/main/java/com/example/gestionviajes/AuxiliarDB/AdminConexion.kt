package com.example.gestionviajes.AuxiliarDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
class AdminConexion(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        Log.e("APS", "Paso por OnCreate del AdminSQLLite")

        db.execSQL("create table empleados(nombre text primary key, telefono text)")
        db.execSQL("create table camiones(nombre text primary key, marca text,km text)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.e("APS", "Paso por OnUpgrade del AdminSQLLite")

        db.execSQL("create table empleados(nombre text primary key, telefono text)")
        db.execSQL("create table camiones(nombre text primary key, marca text,km text)")
    }
}