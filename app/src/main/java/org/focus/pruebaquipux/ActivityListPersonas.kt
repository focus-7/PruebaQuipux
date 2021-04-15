    package org.focus.pruebaquipux

    import android.content.Intent
    import android.os.Bundle
    import android.widget.LinearLayout
    import androidx.appcompat.app.AppCompatActivity
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import kotlinx.android.synthetic.main.activity_list_personas.*

    class ActivityListPersonas : AppCompatActivity() {
        companion object {
            lateinit var dbHandler: DBHandler
        }


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_list_personas)

            val bundle = intent.extras
            val email = bundle?.getString("email")

            dbHandler = DBHandler(this, null, null, 1)
            setup(email ?: "")

            vistaPersonas()

            btnCreate.setOnClickListener {
                if (switchPersona.isChecked) {
                    val i = Intent(this, ActivityPersona::class.java)
                    startActivity(i)
                } else {
                    android.widget.Toast.makeText(
                        this,
                        "No esta habilitado para agregar personas",
                        android.widget.Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

        private fun setup(email: String) {
            title = "ListaPersonas"
        }

        private fun vistaPersonas() {
            val personasLista: ArrayList<Persona> = dbHandler.getPersonas(this)
            val adapter = PersonaAdapter(this, personasLista)
            val rv: RecyclerView = findViewById(R.id.rvListaPersonas)
            rv.layoutManager =
                LinearLayoutManager(this, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager
            rv.adapter = adapter
        }

        override fun onResume() {
            vistaPersonas()
            super.onResume()
        }
    }