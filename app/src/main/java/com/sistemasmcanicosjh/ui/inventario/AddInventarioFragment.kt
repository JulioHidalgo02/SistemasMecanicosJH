package com.sistemasmcanicosjh.ui.inventario

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
import com.sistemasmcanicosjh.databinding.FragmentAddInventarioBinding
import com.sistemasmcanicosjh.model.Inventario
import com.sistemasmcanicosjh.utiles.ImagenUtiles
import com.sistemasmcanicosjh.viewmodel.InventarioViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddInventarioFragment : Fragment() {
        private var _binding: FragmentAddInventarioBinding? = null
        private val binding get() = _binding!!

        private lateinit var imagenUtiles: ImagenUtiles
        private lateinit var tomarFotoActivity: ActivityResultLauncher<Intent>

        private lateinit var  inventarioViewModel: InventarioViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddInventarioBinding.inflate(inflater, container, false)

        inventarioViewModel = ViewModelProvider(this).get(InventarioViewModel::class.java)

        binding.btUpdated.setOnClickListener{
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
            binding.btPhotoInventario,
            binding.btRotaLInventario,
            binding.btRotaRInventario,
            binding.imagen,
            tomarFotoActivity)
        return binding.root
    }

    private fun insertaObjeto(rutaImagen: String) {
        val nombre = binding.etNombre.text.toString()
        val marca = binding.etMarca.text.toString()
        val cantidad = binding.etCantidad.text.toString().toInt()
        val estado = binding.etEstado.text.toString()

        if(nombre != null){
            val obj = Inventario(0,nombre, marca, cantidad, estado,rutaImagen)
            inventarioViewModel.addInventario(obj)
            Toast.makeText(requireContext(),getString(R.string.msgObjAgregado),Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addInventarioFragment_to_nav_inventario)
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
}