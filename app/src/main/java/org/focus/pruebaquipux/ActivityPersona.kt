package org.focus.pruebaquipux

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_persona.*
import java.util.regex.Pattern

class ActivityPersona : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persona)

        llenarCamposSeleccion()

        btnRegister.setOnClickListener {
            if (edtNombres.text.isNullOrEmpty() || edtApellidos.text.isNullOrEmpty() ||
                edtNumIdentificacion.text.isNullOrEmpty()
            ) {
                Toast.makeText(this, "Los campos con * son requeridos", Toast.LENGTH_SHORT).show()
            } else if (!validarIdentificacion()) {
                Toast.makeText(
                    this,
                    "El número de identificación es incorrecto",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (edtEdad.text.toString().toInt() < 18) {
                Toast.makeText(this, "La edad deber ser mayor de 18 años", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val persona = Persona()
                persona.personaNombres = edtNombres.text.toString()
                persona.personaApellidos = edtApellidos.text.toString()
                persona.personaIdentificacion = edtNumIdentificacion.text.toString()
                persona.personaEdad = edtEdad.text.toString().toInt()
                persona.personaSexo = edtSexo.text.toString()
                persona.personaTipoID = edtTipoID.text.toString()

                if (edtEdad.text.toString().toInt() > 60) {
                    Toast.makeText(
                        this,
                        "Vamos a ingresar una persona de la tercera edad, su edad es: ${persona.personaEdad}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                ActivityListPersonas.dbHandler.agregarPersonas(this, persona)
                limpiarEditables()

                val i = Intent(this, ActivityListPersonas::class.java)
                startActivity(i)
            }
        }
        btnCancel.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(this)
            builder.setTitle("Aviso")
            builder.setMessage("¿Está seguro de cancelar?")
            builder.setNegativeButton(
                "SI"
            ) { dialog, which ->
                limpiarEditables()
                finish()
                dialog.dismiss()
            }
            builder.setPositiveButton(
                "NO"
            ) { dialog, which ->
                dialog.dismiss()
            }
            builder.show()
        }
    }

    private fun validarIdentificacion(): Boolean {
        val numero = edtNumIdentificacion.text.toString()

        if (edtTipoID.text.toString().equals("Cédula")) {
            return Pattern.matches("^([0-9]){1,10}\$", numero)
        } else if (edtTipoID.text.toString().equals("Tarjeta identidad")) {
            return Pattern.matches("^([0-9]){1,11}\$", numero)
        } else {
            return numero.length <= 15
        }
    }

    private fun llenarCamposSeleccion() {

        val tipo = arrayOf(
            "Cédula", "Tarjeta identidad", "Pasaporte"
        )
        val adapterID: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.dropdown_menu_popup_item,
            tipo
        )
        edtTipoID.setAdapter(adapterID)


        val sexo = arrayOf(
            "Masculino", "Femenino", "Otros"
        )
        val adapterSexo: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.dropdown_menu_popup_item,
            sexo
        )
        edtSexo.setAdapter(adapterSexo)
    }


    private fun limpiarEditables() {
        edtApellidos.text?.clear()
        edtNombres.text?.clear()
        edtEdad.text?.clear()
        edtNumIdentificacion.text?.clear()
    }
}