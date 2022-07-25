package com.sistemasmcanicosjh.ui.clientes

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
import com.sistemasmcanicosjh.databinding.FragmentUpdateClientesBinding
import com.sistemasmcanicosjh.model.Clientes
import com.sistemasmcanicosjh.viewmodel.ClientesViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class UpdateClientesFragment : Fragment() {

    private val args by navArgs<UpdateClientesFragmentArgs>()

        private var _binding: FragmentUpdateClientesBinding? = null
        private val binding get() = _binding!!

        private lateinit var  clientesViewModel: ClientesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateClientesBinding.inflate(inflater, container, false)

        clientesViewModel = ViewModelProvider(this).get(ClientesViewModel::class.java)

        binding.etNombreCompleto.setText(args.cliente.nombreCompleto)
        binding.etCorreo.setText(args.clientes.correo)
        binding.etRutaImagen.setText(args.rutaImagen.correo)
        binding.etLatitud.setText(args.clientes.latitud.toString())
        binding.etLongitud.setText(args.clientes.longitud.toString())
        binding.etAltura.setText(args.clientes.altura.toString())


        binding.btUpdated.setOnClickListener{ updateObjeto()}

        return binding.root
    }

    private fun updateObjeto() {
        val nombreCompleto = binding.etNombreCompleto.text.toString()
        val correo = binding.etCorreo.text.toString()
        val latitud = binding.etLatitud.text.toString().toDouble()
        val longitud = binding.etLongitud.text.toString().toDouble()
        val altura = binding.etAltura.text.toString().toDouble()
        val rutaImagen = binding.etRutaImagen.text.toString()


      if(nombreCompleto.isNotEmpty()){
            val clientes = Clientes(args.clientes.id,nombreCompleto, correo, latitud, longitud,altura,rutaImagen)
            clientesViewModel.updateClientes(clientes)
            Toast.makeText(requireContext(),getString(R.string.msgClientesActualizado),Toast.LENGTH_LONG).show()
        } else{
            Toast.makeText(requireContext(),getString(R.string.msgFaltanDatos),Toast.LENGTH_LONG).show()
        }
        findNavController().navigate(R.id.action_updateClientesFragment2_to_nav_clientes)
    }
}