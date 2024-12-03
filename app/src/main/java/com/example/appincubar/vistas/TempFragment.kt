package com.example.appincubar.vistas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.appincubar.R
import com.google.android.material.appbar.MaterialToolbar

//parametros del fragmento
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TempFragment : Fragment() {

    //variables para los parametros
    private var param1: String? = null
    private var param2: String? = null

    //declarar la variable para la top app bar
    private lateinit var topAppBar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_temp, container, false)

        //inicializar la top app bar
        topAppBar = binding.findViewById(R.id.topAppBar)

        //configuraar boton de retroseso
        topAppBar.setNavigationOnClickListener {
            //mostrar advertencia antes de retroceder
            showExitConfirmationDialog()
        }

        return binding
    }

    //metodo para mostrar el cuadro de confirmacion de salida
    private fun showExitConfirmationDialog() {
        //crear el dialogo de confirmacion
        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Salir del fragmento")
            .setMessage("¿Estás seguro de que quieres salir de esta sección?  La sesión se cerrará.")
            .setCancelable(false)
            .setPositiveButton("Sí") { _, _ ->
                //si el usuario confirma retrocedemos al fragmento anterior
                activity?.onBackPressed()
            }
            .setNegativeButton("No") { dialog, _ ->
                //cerrar dialogo si se cancela
                dialog.dismiss()
            }

        builder.show()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TempFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
