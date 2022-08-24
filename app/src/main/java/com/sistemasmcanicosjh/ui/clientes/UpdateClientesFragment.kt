package com.sistemasmcanicosjh.ui.clientes

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
        binding.btEmail.setOnClickListener{ EnviarCorreo()}
        binding.btLocation.setOnClickListener{ Ubicacion()}
        binding.btWhatsapp.setOnClickListener{ ContactarWhatsApp()}
        binding.btPhone.setOnClickListener{ LlamarCliente()}

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


    private fun ContactarWhatsApp() {
        val telefono = binding.etTelefono.text
        if(telefono.isNotEmpty()){
            val sendIntent = Intent(Intent.ACTION_VIEW)
            val uri =
                "whatsapp://send?phone=506$telefono&text=" +
                        getString(R.string.msg_saludos)
            sendIntent.setPackage("com.whatsapp")
            sendIntent.data = Uri.parse(uri)
            startActivity(sendIntent)
        }else{
            Toast.makeText(requireContext(),
                getString(R.string.msg_datos), Toast.LENGTH_SHORT).show()
        }
    }

    private fun Ubicacion() {
        val latitud=binding.etLatitud.text.toString().toDouble()
        val longitud=binding.etLongitud.text.toString().toDouble()
        if(latitud.isFinite() && longitud.isFinite()){
            val location = Uri.parse("geo:$latitud,$longitud?z18")
            val mapIntent = Intent(Intent.ACTION_VIEW,location)
            startActivity(mapIntent)
        }else{

        }

    }

    private fun LlamarCliente() {

        val recurso = binding.etTelefono.text.toString()
        if (recurso.isNotEmpty()){

            val rutina = Intent(Intent.ACTION_CALL)
            rutina.data = Uri.parse("tel:$recurso")
            if(requireActivity().checkSelfPermission(Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED){
                requireActivity()
                    .requestPermissions(arrayOf(Manifest.permission.CALL_PHONE),105)
            }else{
                requireActivity().startActivity(rutina)
            }
            startActivity(rutina)
        }else{
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_datos),Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun EnviarCorreo() {
        //Se recupera el correo del lugar
        val recurso = binding.etCorreo.text.toString()
        if (recurso.isNotEmpty()){
            //Se activa el correo
            val rutina = Intent(Intent.ACTION_SEND)
            rutina.type="message/rfc822"
            rutina.putExtra(Intent.EXTRA_EMAIL, arrayOf(recurso))
            rutina.putExtra(
                Intent.EXTRA_SUBJECT,
                getString(R.string.msg_saludos) + " " + binding.etNombreCompleto.text)
            rutina.putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.msg_mensaje_correo))
            startActivity(rutina)
        }else{
            Toast.makeText(
                requireContext(),
                getString(R.string.msg_datos),Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}