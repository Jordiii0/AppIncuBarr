package com.example.appincubar

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appincubar.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {

    //enlazamos el diseño con la logica mediante View Binding
    private lateinit var binding: ActivityMainBinding
    //instancia para manejar la autenticación de Firebase
    private lateinit var auth: FirebaseAuth
    //referencia a la base de datos
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inicializar el enlace con la vista
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //obtener la instancia de autenticacion de firebase
        auth = Firebase.auth

        //configurar click en el boton de inicio de sesion
        binding.btnLogin.setOnClickListener {
            //obtener valores que ingresa el usuario
            val correo = binding.etEmail.text.toString()
            val contrasena = binding.etPassword.text.toString()

            //validar que los campos no esten vacios
            if (correo.isEmpty()) {
                binding.etEmail.error = "Ingrese un correo"
                return@setOnClickListener
            }
            if (contrasena.isEmpty()) {
                binding.etPassword.error = "Ingrese una contraseña"
                return@setOnClickListener
            }

            //try catch para manejar excepciones
            try {
                signIn(correo, contrasena)
            } catch (e: Exception) {
                //mostrar mensaje de error
                Toast.makeText(this, "Error al iniciar sesión: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    //funcion para iniciar sesion con la firebase
    private fun signIn(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        //mensaje de exito
                        Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, PostLoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        //mensaje de error
                        Toast.makeText(this, "Error: ${it.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        } catch (e: Exception) {
            //cualquier error presente muestra este mensaje
            Toast.makeText(this, "Excepción durante el inicio de sesión: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
