package org.focus.pruebaquipux

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHandler(
    context: Context,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object {
        private val DATABASE_NAME = "Personas.db"
        private val DATABASE_VERSION = 1

        val PERSONAS_TABLA = "Personas"
        val COLUMNA_PERSONA_NOMBRES = "nombre"
        val COLUMNA_PERSONA_APELLIDOS = "apellidos"
        val COLUMNA_PERSONA_IDENTIFICACION = "identificacion"
        val COLUMNA_PERSONA_ID = "id"
        val COLUMNA_PERSONA_TIPO_ID = "tipo_identificacion"
        val COLUMNA_PERSONA_SEXO = "sexo"
        val COLUMNA_PERSONA_EDAD = "edad"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREAR_TABLA_PERSONAS: String = ("CREATE TABLE $PERSONAS_TABLA (" +
                "$COLUMNA_PERSONA_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMNA_PERSONA_NOMBRES TEXT," +
                "$COLUMNA_PERSONA_SEXO TEXT," +
                "$COLUMNA_PERSONA_APELLIDOS TEXT," +
                "$COLUMNA_PERSONA_EDAD INTEGER," +
                "$COLUMNA_PERSONA_TIPO_ID TEXT," +
                "$COLUMNA_PERSONA_IDENTIFICACION TEXT)")
        db?.execSQL(CREAR_TABLA_PERSONAS)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun getPersonas(context: Context): ArrayList<Persona> {
        val query = "Select * from $PERSONAS_TABLA"
        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = db.rawQuery(query, null)
        val personas = ArrayList<Persona>()

        if (cursor.count == 0)
            Toast.makeText(
                context,
                "Esto es una prueba de Android y necesitamos personas para mostrar, por favor agregue personas",
                Toast.LENGTH_SHORT
            ).show()
        else
            while (cursor.moveToNext()) {
                val persona = Persona()
                persona.personaID = cursor.getInt(cursor.getColumnIndex(COLUMNA_PERSONA_ID))
                persona.personaNombres = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMNA_PERSONA_NOMBRES
                    )
                )
                persona.personaApellidos = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMNA_PERSONA_APELLIDOS
                    )
                )
                persona.personaEdad = cursor.getInt(cursor.getColumnIndex(COLUMNA_PERSONA_EDAD))
                persona.personaIdentificacion = cursor.getString(
                    cursor.getColumnIndex(
                        COLUMNA_PERSONA_IDENTIFICACION
                    )
                )
                persona.personaSexo = cursor.getString(cursor.getColumnIndex(COLUMNA_PERSONA_SEXO))
                persona.personaTipoID =
                    cursor.getString(cursor.getColumnIndex(COLUMNA_PERSONA_TIPO_ID))
                personas.add(persona)
            }
        Toast.makeText(
            context,
            "${cursor.count.toString()} Registros Encontrados",
            Toast.LENGTH_SHORT
        ).show()
        cursor.close()
        db.close()
        return personas
    }

    fun agregarPersonas(context: Context, persona: Persona) {
        val values = ContentValues()
        values.put(COLUMNA_PERSONA_NOMBRES, persona.personaNombres)
        values.put(COLUMNA_PERSONA_SEXO, persona.personaSexo)
        values.put(COLUMNA_PERSONA_APELLIDOS, persona.personaApellidos)
        values.put(COLUMNA_PERSONA_EDAD, persona.personaEdad)
        values.put(COLUMNA_PERSONA_TIPO_ID, persona.personaTipoID)
        values.put(COLUMNA_PERSONA_IDENTIFICACION, persona.personaIdentificacion)
        val db = this.writableDatabase
        try {
            db.insert(PERSONAS_TABLA, null, values)
            Toast.makeText(context, "Persona Agregada", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()
    }
}