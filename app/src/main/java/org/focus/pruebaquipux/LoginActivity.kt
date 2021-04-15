package org.focus.pruebaquipux

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setup()
    }

    private fun setup() {
        title = "Autentication"

        btnSignIn.setOnClickListener {
            if (editTextTextEmail.text.isNotEmpty() && editTextTextPassword.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    editTextTextEmail.text.toString(),
                    editTextTextPassword.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful)
                        showListPersonas(it.result?.user?.email ?: "")
                    else
                        showAlert()
                }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error de autenticación")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showListPersonas(email: String) {
        val personasListIntent = Intent(this, ActivityListPersonas::class.java).apply {
            putExtra("email", email)
        }
        startActivity(personasListIntent)
    }


}