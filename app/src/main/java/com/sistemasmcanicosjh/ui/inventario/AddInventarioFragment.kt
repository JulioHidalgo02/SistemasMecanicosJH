package com.sistemasmcanicosjh.ui.inventario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.sistemasmcanicosjh.R
import com.sistemasmcanicosjh.databinding.FragmentAddInventarioBinding
import com.sistemasmcanicosjh.model.Inventario
import com.sistemasmcanicosjh.viewmodel.InventarioViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddInventarioFragment : Fragment() {
        private var _binding: FragmentAddInventarioBinding? = null
        private val binding get() = _binding!!

        private lateinit var  inventarioViewModel: InventarioViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddInventarioBinding.inflate(inflater, container, false)

        inventarioViewModel = ViewModelProvider(this).get(InventarioViewModel::class.java)

        binding.btUpdated.setOnClickListener{ insertaObjeto()}

        return binding.root
    }

    private fun insertaObjeto() {
        val nombre = binding.etNombre.text.toString()
        val marca = binding.etMarca.text.toString()
        val cantidad = binding.etCantidad.text.toString().toInt()
        val estado = binding.etEstado.text.toString()

        if(nombre != null){
            val obj = Inventario(0,nombre, marca, cantidad, estado)
            inventarioViewModel.addInventario(obj)
            Toast.makeText(requireContext(),getString(R.string.msgObjAgregado),Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(requireContext(),getString(R.string.msgObjError),Toast.LENGTH_LONG).show()
        }
    }
}