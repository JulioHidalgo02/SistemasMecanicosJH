package com.sistemasmcanicosjh.ui.clientes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.sistemasmcanicosjh.R
import com.sistemasmcanicosjh.databinding.FragmentAddClientesBinding
import com.sistemasmcanicosjh.model.Clientes
import com.sistemasmcanicosjh.viewmodel.ClientesViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddClientesFragment : Fragment() {
        private var _binding: FragmentAddClientesBinding? = null
        private val binding get() = _binding!!

        private lateinit var  clientesViewModel: ClientesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddClientesBinding.inflate(inflater, container, false)

        clientesViewModel = ViewModelProvider(this).get(ClientesViewModel::class.java)

        binding.btAgregar.setOnClickListener{ insertaObjeto()}

        return binding.root
    }

    private fun insertaObjeto() {
        val nombreCompleto = binding.etNombreCompleto.text.toString()
        val correo = binding.etCorreo.text.toString()
        val latitud = binding.etLatitud.text.toString().toDouble()
        val longitud = binding.etLongitud.text.toString().toDouble()
        val altura = binding.etAltura.text.toString().toDouble()
        val rutaImagen = binding.etRutaImagen.text.toString()

        if(nombreCompleto != null){
            val obj = Clientes("",nombreCompleto, correo, latitud, longitud,altura,rutaImagen)
            clientesViewModel.addClientes(obj)
            Toast.makeText(requireContext(),getString(R.string.msgObjAgregado),Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(requireContext(),getString(R.string.msgObjError),Toast.LENGTH_LONG).show()
        }
    }
}