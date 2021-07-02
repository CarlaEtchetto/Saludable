package carla.etchetto.saludable.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import carla.etchetto.saludable.Modelos.Comida
import carla.etchetto.saludable.Modelos.Usuario
import java.lang.Exception

class dbHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, BASE_DE_DATOS, factory, VERSION_DB) {

    companion object {
        private val BASE_DE_DATOS = "saludable.db"
        private val VERSION_DB = 2
        val TABLE_USUARIOS = "usuarios"
        val TABLE_COMIDAS ="comidas"
        val COLUMN_NOMBRE = "nombre"
        val COLUMN_APELLIDO = "apellido"
        val COLUMN_DNI = "dni"
        val COLUMN_FECHA_NACIMIENTO = "fechaNacimiento"
        val COLUMN_LOCALIDAD = "localidad"
        val COLUMN_USUARIO ="usuario"
        val COLUMN_CONTRASENIA="pass"
        val COlUMN_TRATAMIENTO="tratamiento"
        val COLUMN_SEXO="sexo"
        val COLUMN_USUARIO_COMIDAS ="id_comidas"
        val COLUMN_NOMBRE_COMIDA="nombre_comida"
        val COLUMN_COMIDA_PPAL ="comida_principal"
        val COLUMN_COMIDA_SRIA ="comida_secundaria"
        val COLUMN_BEBIDA ="bebida"
        val COLUMN_POSTRE ="postre"
        val COLUMN_COMIDA_EXTRA="comida_extra"
        val COLUMN_FECHA_REGISTRO="fechaRegistro"
        val COLUMN_SATISFECHO = "satisfecho"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val crearTablaUsuarios = ("CREATE TABLE "+ TABLE_USUARIOS +
                " ( " +
                COLUMN_NOMBRE + " TEXT," +
                COLUMN_APELLIDO + " TEXT," +
                COLUMN_DNI + " INTEGER," +
                COLUMN_FECHA_NACIMIENTO + " TEXT," +
                COLUMN_LOCALIDAD + " TEXT," +
                COLUMN_USUARIO + " TEXT," +
                COLUMN_CONTRASENIA + " TEXT," +
                COlUMN_TRATAMIENTO + " TEXT," +
                COLUMN_SEXO + " TEXT" + ")")

        val crearTablaComidas = ("CREATE TABLE " + TABLE_COMIDAS +
                " ( " + COLUMN_USUARIO_COMIDAS + " TEXT," +
                COLUMN_NOMBRE_COMIDA + " TEXT," +
                COLUMN_COMIDA_PPAL + " TEXT," +
                COLUMN_COMIDA_SRIA + " TEXT," +
                COLUMN_BEBIDA + " TEXT," +
                COLUMN_POSTRE + " TEXT," +
                COLUMN_COMIDA_EXTRA + " TEXT," +
                COLUMN_SATISFECHO + " TEXT," +
                COLUMN_FECHA_REGISTRO + " TEXT" + ")")

        db?.execSQL(crearTablaUsuarios)
        db?.execSQL(crearTablaComidas)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS)
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_COMIDAS)

        onCreate(db)
    }

    fun guardarUsuario(usuario: Usuario) {
        var db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_NOMBRE, usuario.nombre)
        values.put(COLUMN_APELLIDO, usuario.apellido)
        values.put(COLUMN_DNI, usuario.dni)
        values.put(COLUMN_FECHA_NACIMIENTO, usuario.fechaNacimiento)
        values.put(COLUMN_LOCALIDAD, usuario.localidad)
        values.put(COLUMN_USUARIO, usuario.usuario)
        values.put(COLUMN_CONTRASENIA, usuario.pass)
        values.put(COlUMN_TRATAMIENTO, usuario.tratamiento)
        values.put(COLUMN_SEXO, usuario.sexo)

        try {
            db.insert(TABLE_USUARIOS, null, values)
        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }
    }
    fun guardarComida(comida: Comida) {
        var dbs = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USUARIO_COMIDAS,comida.paciente)
        values.put(COLUMN_NOMBRE_COMIDA, comida.nombre)
        values.put(COLUMN_COMIDA_PPAL, comida.comidaPpal)
        values.put(COLUMN_COMIDA_SRIA, comida.comidaSria)
        values.put(COLUMN_BEBIDA, comida.bebida)
        values.put(COLUMN_POSTRE, comida.postre)
        values.put(COLUMN_COMIDA_EXTRA, comida.comidaExtra)
        values.put(COLUMN_FECHA_REGISTRO,comida.fechaRegistro)
        values.put(COLUMN_SATISFECHO,comida.satisfecho)

        try {
            dbs.insert(TABLE_COMIDAS, null, values)
        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }
    }



    fun validarUsuarioLogin(usuario: String, pass: String): Boolean {

        var db = this.readableDatabase
        var query = "SELECT * FROM " + TABLE_USUARIOS
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val nameDB = cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO))
                val passDB = cursor.getString(cursor.getColumnIndex(COLUMN_CONTRASENIA))
                if (usuario.equals(nameDB) && pass.equals(passDB)) {
                    return true
                }
            } while (cursor.moveToNext())
        }

        return false

    }


    fun NombreUsuarioUtilizado(usuario: String) : Boolean
    {
        var db = this.readableDatabase
        var busqueda = "SELECT * FROM " + TABLE_USUARIOS
        val cursor = db.rawQuery(busqueda,null)
        if (cursor.moveToFirst()){
            do {
                val usuarioEnBase = cursor.getString(cursor.getColumnIndex(COLUMN_USUARIO))
                if(usuario.equals(usuarioEnBase))
                {
                    return true
                }
            } while (cursor.moveToNext())

        }
        return false
    }


}

