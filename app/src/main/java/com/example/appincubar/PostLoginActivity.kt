package com.example.appincubar

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appincubar.databinding.ActivityPostLoginBinding
import com.example.appincubar.vistas.DiasFragment
import com.example.appincubar.vistas.HomeFragment
import com.example.appincubar.vistas.HumedadFragment
import com.example.appincubar.vistas.PersonFragment
import com.example.appincubar.vistas.TempFragment

class PostLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inicializar binding para acceder a los elementos de la interfaz
        binding = ActivityPostLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            //comprobar si no hay un estado guardado y cargamos el fragmento inicial
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, HomeFragment()).commit()
            }
        } catch (e: Exception) {
            //capturar cualquier error al cargar el fragmento inicial
            Toast.makeText(this, "Error al cargar la vista inicial: ${e.message}", Toast.LENGTH_LONG).show()
        }

        //configurar el listener para manejar la seleccion de items en la barra de navegacion
        binding.bottonNav.setOnItemSelectedListener {
            try {
                when (it.itemId) {
                    R.id.nav_home -> {
                        //navegar al fragmento de inicio
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, HomeFragment()).commit()
                        true
                    }
                    R.id.nav_dias -> {
                        //navegar al fragmento de dias
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, DiasFragment()).commit()
                        true
                    }
                    R.id.nav_temp -> {
                        //navegar al fragmento de temperatura
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, TempFragment()).commit()
                        true
                    }
                    R.id.nav_humedad -> {
                        //navegar al fragmento de humedad
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, HumedadFragment()).commit()
                        true
                    }
                    R.id.nav_user -> {
                        //navegar al fragmento de usuario
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, PersonFragment()).commit()
                        true
                    }
                    else -> false
                }
            } catch (e: Exception) {
                //capturar cualquier error al cambiar de fragmento
                Toast.makeText(this, "Error al cambiar de vista: ${e.message}", Toast.LENGTH_LONG).show()
                false
            }
        }

        //configurar el listener para manejar la re-selección de items en la barra de navegacion
        binding.bottonNav.setOnItemReselectedListener {
            try {
                when (it.itemId) {
                    R.id.nav_home -> Toast.makeText(this, "Ya estás en la vista de Inicio", Toast.LENGTH_SHORT).show()
                    R.id.nav_dias -> Toast.makeText(this, "Ya estás en la vista de Dias", Toast.LENGTH_SHORT).show()
                    R.id.nav_temp -> Toast.makeText(this, "Ya estás en la vista de Temperatura", Toast.LENGTH_SHORT).show()
                    R.id.nav_humedad -> Toast.makeText(this, "Ya estás en la vista de Humedad", Toast.LENGTH_SHORT).show()
                    R.id.nav_user -> Toast.makeText(this, "Ya estás en la vista de Usuario", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                //capturamos cualquier error al manejar re-selecciones
                Toast.makeText(this, "Error al procesar la re-selección: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
