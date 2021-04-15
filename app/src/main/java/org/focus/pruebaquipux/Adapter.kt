package org.focus.pruebaquipux

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_persona_adapter.view.*

class PersonaAdapter(context: Context, val personas: ArrayList<Persona>) :
    RecyclerView.Adapter<PersonaAdapter.ViewHolder>() {
    val context = context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre = itemView.nombrePersona
        val txtApellido = itemView.apellidosPersona
        val txtEdad = itemView.edadPersona
        val txtTipoID = itemView.tipoDocumento
        val txtNumID = itemView.numeroDocumento
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_persona_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return personas.size
    }

    override fun onBindViewHolder(holder: PersonaAdapter.ViewHolder, position: Int) {
        val persona: Persona = personas[position]

        holder.txtNombre.text = persona.personaNombres
        holder.txtApellido.text = persona.personaApellidos
        holder.txtEdad.text = persona.personaEdad.toString()
        holder.txtNumID.text = persona.personaIdentificacion
        holder.txtTipoID.text = persona.personaTipoID
    }

}