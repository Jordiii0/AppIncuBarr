package com.example.appincubar

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appincubar.databinding.ActivityCambiarPassBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class CambiarPassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCambiarPassBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCambiarPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        //configurar boton de retroceso
        binding.btnBack.setOnClickListener {
            onBackPressed() //retroceder a la vista anterior
        }

        //configurar el boton para cambiar la contraseña
        binding.btnCambiarPassword.setOnClickListener {
            cambiarContrasena()
        }
    }

    private fun cambiarContrasena() {
        //obtener usuario autenticado
        val user = auth.currentUser

        //verificar que usuario no sea nulo
        if (user != null) {
            //obtener los valores ingresados por el usuario
            val contraActual = binding.etContraActual.text.toString()
            val contraNueva = binding.etNewPassword.text.toString()
            val contraNueva2 = binding.etNewPassword2.text.toString()

            //validar que los campos no esten vacios
            if (contraActual.isEmpty() || contraNueva.isEmpty() || contraNueva2.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_LONG)
                    .show()
                return
            }

            //crear las credenciales usando el correo del usuario y la contraseña actual ingresada
            val credential = EmailAuthProvider.getCredential(user.email!!, contraActual)

            //reautenticamos al usuario con las credenciales proporcionadas
            user.reauthenticate(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //si la reautenticacion es exitosa, verificamos si las nuevas contraseñas coinciden
                        if (contraNueva == contraNueva2) {
                            //actualizar la contraseña del usuario
                            user.updatePassword(contraNueva)
                                .addOnCompleteListener { updateTask ->
                                    if (updateTask.isSuccessful) {
                                        //mostrar mensaje si la contraseña es exitosa
                                        Toast.makeText(
                                            this,
                                            "Contraseña actualizada correctamente.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    } else {
                                        //mensaaje de error
                                        Toast.makeText(
                                            this,
                                            "Error al actualizar la contraseña.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        } else {
                            //advertencia si las contraseñas no coinciden
                            Toast.makeText(
                                this,
                                "Las contraseñas nuevas no coinciden.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        //mensaje de exito
                        Toast.makeText(
                            this,
                            "La contraseña actual es incorrecta.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        } else {
            //mensaje de error si el usuario no esta autenticado
            Toast.makeText(this, "No se pudo autenticar al usuario.", Toast.LENGTH_LONG).show()
        }
    }
}
