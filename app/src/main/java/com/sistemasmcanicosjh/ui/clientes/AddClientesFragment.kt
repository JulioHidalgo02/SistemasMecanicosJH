package com.sistemasmcanicosjh.ui.clientes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.sistemasmcanicosjh.R
import com.sistemasmcanicosjh.databinding.FragmentAddClientesBinding
import com.sistemasmcanicosjh.model.Clientes
import com.sistemasmcanicosjh.utiles.ImagenUtiles
import com.sistemasmcanicosjh.viewmodel.ClientesViewModel



class AddClientesFragment : Fragment() {
        private var _binding: FragmentAddClientesBinding? = null
        private val binding get() = _binding!!

        private lateinit var imagenUtiles: ImagenUtiles
        private lateinit var tomarFotoActivity: ActivityResultLauncher<Intent>

        private lateinit var  clientesViewModel: ClientesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddClientesBinding.inflate(inflater, container, false)

        clientesViewModel = ViewModelProvider(this).get(ClientesViewModel::class.java)

        binding.btAgregar.setOnClickListener{
            binding.progressBar.visibility = ProgressBar.VISIBLE
            binding.msgMensaje.text = getString(R.string.msg_subiendo_imagen)
            binding.msgMensaje.visibility = TextView.VISIBLE
            subeImagen()
        }

        tomarFotoActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
                result ->
            if (result.resultCode == Activity.RESULT_OK){
                imagenUtiles.actualizaFoto()
            }
        }

        imagenUtiles = ImagenUtiles(
            requireContext(),
            binding.btPhoto,
            binding.btRotaL,
            binding.btRotaR,
            binding.imagen,
            tomarFotoActivity)

        return binding.root
    }

    private fun insertaObjeto(rutaImagen: String) {
        val nombreCompleto = binding.etNombreCompleto.text.toString()
        val correo = binding.etCorreo.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val latitud = binding.etLatitud.text.toString().toDouble()
        val longitud = binding.etLongitud.text.toString().toDouble()

        if(nombreCompleto != null){
            val obj = Clientes("",nombreCompleto, correo,telefono, latitud, longitud,rutaImagen)
            clientesViewModel.addClientes(obj)
            Toast.makeText(requireContext(),getString(R.string.msgObjAgregado),Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addClientesFragment_to_nav_clientes)
        }else{
            Toast.makeText(requireContext(),getString(R.string.msgObjError),Toast.LENGTH_LONG).show()
        }
    }

    private fun subeImagen() {
        binding.msgMensaje.text = getString(R.string.msg_subiendo_imagen)
        if (imagenUtiles.getFotoTomada()) {
            val imagenFile = imagenUtiles.imagenFile
            if (imagenFile.exists() && imagenFile.isFile && imagenFile.canRead()) {
                val ruta = Uri.fromFile(imagenFile)
                val rutaNube =
                    "sistemasMecanicosApp/${Firebase.auth.currentUser?.email}/imagenes/${imagenFile.name}"
                val referencia: StorageReference = Firebase.storage.reference.child(rutaNube)
                referencia.putFile(ruta)
                    .addOnSuccessListener {
                        referencia.downloadUrl
                            .addOnSuccessListener {
                                val rutaImagen = it.toString()
                                insertaObjeto(rutaImagen)
                            }
                    }
                    .addOnFailureListener {
                        insertaObjeto("")
                    }

            } else {
                insertaObjeto("")
            }
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }
}