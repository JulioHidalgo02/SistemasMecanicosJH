package com.sistemasmcanicosjh.ui.inventario

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sistemasmcanicosjh.R
import com.sistemasmcanicosjh.databinding.FragmentUpdateInventarioBinding
import com.sistemasmcanicosjh.model.Inventario
import com.sistemasmcanicosjh.viewmodel.InventarioViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class UpdateInventarioFragment : Fragment() {

    private val args by navArgs<UpdateInventarioFragmentArgs>()

        private var _binding: FragmentUpdateInventarioBinding? = null
        private val binding get() = _binding!!

        private lateinit var  inventarioViewModel: InventarioViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateInventarioBinding.inflate(inflater, container, false)

        inventarioViewModel = ViewModelProvider(this).get(InventarioViewModel::class.java)

        binding.etNombre.setText(args.inventario.nombre)
        binding.etMarca.setText(args.inventario.marca)
        binding.etCantidad.setText(args.inventario.cantidad.toString())
        binding.etEstado.setText(args.inventario.estado)

        binding.btUpdated.setOnClickListener{ updateObjeto()}
        binding.btDeleteInventario.setOnClickListener{ deleteInventario()}

        return binding.root
    }

    private fun updateObjeto() {
        val nombre = binding.etNombre.text.toString()
        val marca = binding.etMarca.text.toString()
        val cantidad = binding.etCantidad.text.toString().toInt()
        val estado = binding.etEstado.text.toString()


      if(nombre.isNotEmpty()){
            val inventario = Inventario(args.inventario.id,nombre,marca,cantidad,estado)
            inventarioViewModel.updateInventario(inventario)
            Toast.makeText(requireContext(),getString(R.string.msgInventarioActualizado),Toast.LENGTH_LONG).show()
        } else{
            Toast.makeText(requireContext(),getString(R.string.msgFaltanDatos),Toast.LENGTH_LONG).show()
        }
        findNavController().navigate(R.id.action_updateInventarioFragment2_to_nav_inventario)
    }

    private fun deleteInventario(){
        val builder= AlertDialog.Builder(requireContext())

        builder.setTitle(getString(R.string.deleted))
        builder.setMessage(getString(R.string.seguroBorrar) + " ${args.inventario.nombre}")

        builder.setPositiveButton(getString(R.string.si)) {_,_ ->

            inventarioViewModel.deleteInventario(args.inventario)
            findNavController().navigate(R.id.action_updateInventarioFragment2_to_nav_inventario)

        }

        builder.setNegativeButton(getString(R.string.no)){_,_ -> }
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}