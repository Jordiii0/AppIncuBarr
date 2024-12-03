package com.example.appincubar

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appincubar.Models.Incubadora
import com.example.appincubar.databinding.ActivityCrudIncubadoraBinding
import com.example.appincubar.vistas.VerIncubadorasActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class crud_incubadora : AppCompatActivity() {

    private lateinit var binding: ActivityCrudIncubadoraBinding

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudIncubadoraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //inicializar la base de datos
        database = FirebaseDatabase.getInstance().getReference("Incubadoras")

        //configurar la barra superior
        val toolbar = binding.topAppBar
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            mostrarAdvertencia()
        }

        //boton para guardar la incubadora
        binding.btnGuardarIncubadora.setOnClickListener {
            val nombre = binding.etNombreIncubadora.text.toString()
            val dias = binding.etDiasIncubadora.text.toString()
            val temperatura = binding.etTemperaturaIncubadora.text.toString()
            val humedad = binding.etHumedadIncubadora.text.toString()
            val usuario = binding.etUsuarioIncubadora.text.toString()

            val id = database.child("Incubadoras").push().key

            //validar campos
            when {
                nombre.isEmpty() -> {
                    binding.etNombreIncubadora.error = "Por favor ingrese un nombre para la incubadora"
                }
                dias.isEmpty() -> {
                    binding.etDiasIncubadora.error = "Por favor ingrese los días para la incubadora"
                }
                temperatura.isEmpty() -> {
                    binding.etTemperaturaIncubadora.error = "Por favor ingrese la temperatura para la incubadora"
                }
                humedad.isEmpty() -> {
                    binding.etHumedadIncubadora.error = "Por favor ingrese la humedad para la incubadora"
                }
                usuario.isEmpty() -> {
                    binding.etUsuarioIncubadora.error = "Por favor ingrese un nombre de usuario para la incubadora"
                }
                else -> {
                    //crear y guardar la incubadora
                    val incubadora = Incubadora(id, nombre, dias, temperatura, humedad, usuario)
                    database.child(id!!).setValue(incubadora).addOnSuccessListener {
                        Snackbar.make(
                            binding.root,
                            "Incubadora registrada exitosamente",
                            Snackbar.LENGTH_LONG
                        ).show()
                        limpiarCampos()
                    }
                }
            }
        }
        //boton para ver incubadoras
        binding.btnVer.setOnClickListener {
            val intent = Intent(this, VerIncubadorasActivity::class.java)
            startActivity(intent)
        }
    }


    //mostrar advertencia al presionar el boton de retroceso
    private fun mostrarAdvertencia() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmación")
        builder.setMessage("¿Estás seguro de que deseas salir? Los cambios no guardados se perderán.")
        builder.setPositiveButton("Sí") { _, _ ->
            //cierra la actividad
            finish()
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }

    private fun limpiarCampos() {
        binding.etNombreIncubadora.setText("")
        binding.etDiasIncubadora.setText("")
        binding.etTemperaturaIncubadora.setText("")
        binding.etHumedadIncubadora.setText("")
        binding.etUsuarioIncubadora.setText("")
    }

}


