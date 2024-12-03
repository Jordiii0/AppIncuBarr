package com.example.appincubar.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appincubar.Models.Incubadora
import com.example.appincubar.R

// Adaptador personalizado para mostrar una lista de objetos Incubadora en un RecyclerView
class AdapterIncubadora(private val incubadoras: ArrayList<Incubadora>) :
    RecyclerView.Adapter<AdapterIncubadora.ViewHolder>() {

    // clase interna para representar la vista de un solo elemento en el RecyclerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Definir las referencias a los elementos de la vista en el diseño del item
        val nombre: TextView = itemView.findViewById(R.id.tvNombre)
        val dias: TextView = itemView.findViewById(R.id.tvDias)
        val humedad: TextView = itemView.findViewById(R.id.tvHumedad)
        val temperatura: TextView = itemView.findViewById(R.id.tvTemperatura)
        val usuario: TextView = itemView.findViewById(R.id.tvUsuario)
    }

    // metodo llamado cuando se necesita crear un nuevo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflamos el diseño del item y lo pasamos al constructor del ViewHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_incubadoras, parent, false)
        return ViewHolder(view)
    }

    // metodo para obtener la cantidad de elementos en la lista
    override fun getItemCount(): Int {
        return incubadoras.size
    }

    // metodo llamado para enlazar datos con la vista (ViewHolder)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Obtenemos el objeto Incubadora correspondiente a la posición actual
        val incubadora = incubadoras[position]

        // establecer los valores de las propiedades del objeto en los elementos de la vista
        holder.nombre.text = incubadora.nombre
        holder.dias.text = incubadora.dias
        holder.humedad.text = incubadora.humedad
        holder.temperatura.text = incubadora.temperatura
        holder.usuario.text = incubadora.usuario
    }
}
