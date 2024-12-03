package com.example.appincubar.vistas

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appincubar.Adapter.AdapterIncubadora
import com.example.appincubar.Models.Incubadora
import com.example.appincubar.R
import com.example.appincubar.databinding.ActivityVerIncubadorasBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.database.*

class VerIncubadorasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerIncubadorasBinding
    private lateinit var database: DatabaseReference
    private lateinit var incubadorasList: ArrayList<Incubadora>
    private lateinit var adapterIncubadora: AdapterIncubadora

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerIncubadorasBinding.inflate(layoutInflater)
        setContentView(binding.root) // Usa directamente la raíz del binding

        //inicializar la top app bar
        val topAppBar: MaterialToolbar = binding.topAppBar
        topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Usa el controlador correcto
        }

        //configurar RecyclerView
        val incubadorasRecyclerView: RecyclerView = binding.rvIncubadoras
        incubadorasRecyclerView.layoutManager = LinearLayoutManager(this)
        incubadorasRecyclerView.setHasFixedSize(true)

        //inicializar vista y adaptador
        incubadorasList = arrayListOf()
        adapterIncubadora = AdapterIncubadora(incubadorasList)
        incubadorasRecyclerView.adapter = adapterIncubadora

        // cargar datos desde firebase
        getIncubadoras()

    }

    private fun getIncubadoras() {
        database = FirebaseDatabase.getInstance().getReference("Incubadoras")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    incubadorasList.clear()
                    for (incubadoraSnapshot in snapshot.children) {
                        val incubadora = incubadoraSnapshot.getValue(Incubadora::class.java)
                        incubadora?.let { incubadorasList.add(it) }
                    }
                    adapterIncubadora.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                error.toException().printStackTrace()
            }
        })
    }
}
