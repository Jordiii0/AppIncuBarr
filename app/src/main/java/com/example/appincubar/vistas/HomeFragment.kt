package com.example.appincubar.vistas

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.appincubar.R
import com.example.appincubar.crud_incubadora
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    // declarar variables para la top app bar y el boton
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var btnAddIncubator: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_home, container, false)

        // inicializar top app bar
        topAppBar = binding.findViewById(R.id.topAppBar)
        //configurar la accion de retroceder
        topAppBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        // inicializar boton
        btnAddIncubator = binding.findViewById(R.id.btnAddIncubator)
        // configurar el evento de click del boton
        btnAddIncubator.setOnClickListener {
            // Redirigir a la actividad CrudIncubadoraActivity
            val intent = Intent(activity, crud_incubadora::class.java)
            startActivity(intent)
        }

        // configurar la accion del boton de retroceso
        topAppBar.setNavigationOnClickListener {
            // mostrar advertencia al presionar el boton de retroceso
            showExitConfirmationDialog()
        }

        return binding
    }

    private fun showExitConfirmationDialog() {
        // crear el dialogo de confirmacion
        val builder = AlertDialog.Builder(requireContext())
            .setTitle("Salir del fragmento")
            .setMessage("¿Estás seguro de que quieres salir de esta sección? La sesión se cerrará.")
            .setCancelable(false)
            .setPositiveButton("Sí") { _, _ ->
                activity?.onBackPressed()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

        builder.show()
    }

    // inicializar parametros
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
