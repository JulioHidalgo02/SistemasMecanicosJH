package com.sistemasmcanicosjh.ui.clientes

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
import com.bumptech.glide.Glide
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

        binding.etNombreCompleto.setText(args.clintes.nombreCompleto)
        binding.etCorreo.setText(args.clintes.correo)
        binding.etTelefono.setText(args.clintes.telefono)
        binding.etLatitud.setText(args.clintes.latitud.toString())
        binding.etLongitud.setText(args.clintes.longitud.toString())

        if(args.clintes.rutaImagen?.isNotEmpty()==true){
            Glide.with(requireContext())
                .load(args.clintes.rutaImagen)
                .fitCenter()
                .into(binding.imagen)
        }


        binding.btUpdate.setOnClickListener{ updateObjeto()}
        binding.btDelete.setOnClickListener{ deleteClientes()}

        return binding.root
    }

    private fun updateObjeto() {
        val nombreCompleto = binding.etNombreCompleto.text.toString()
        val correo = binding.etCorreo.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val latitud = binding.etLatitud.text.toString().toDouble()
        val longitud = binding.etLongitud.text.toString().toDouble()



      if(nombreCompleto.isNotEmpty()){
            val clientes = Clientes(args.clintes.id,nombreCompleto, correo, telefono, latitud, longitud,args.clintes.rutaImagen)
            clientesViewModel.updateClientes(clientes)
            Toast.makeText(requireContext(),getString(R.string.msgClientesActualizado),Toast.LENGTH_LONG).show()
        } else{
            Toast.makeText(requireContext(),getString(R.string.msgFaltanDatos),Toast.LENGTH_LONG).show()
        }
        findNavController().navigate(R.id.action_updateClientesFragment2_to_nav_clientes)
    }

    private fun deleteClientes(){
        val builder= AlertDialog.Builder(requireContext())

        builder.setTitle(getString(R.string.deleted))
        builder.setMessage(getString(R.string.seguroBorrar) + " ${args.clintes.nombreCompleto}")

        builder.setPositiveButton(getString(R.string.si)) {_,_ ->

            clientesViewModel.deleteClientes(args.clintes)
            findNavController().navigate(R.id.action_updateClientesFragment2_to_nav_clientes)

        }

        builder.setNegativeButton(getString(R.string.no)){_,_ -> }
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}