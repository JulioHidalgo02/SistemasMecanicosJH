package com.sistemasmcanicosjh.ui.clientes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sistemasmcanicosjh.R
import com.sistemasmcanicosjh.adapter.ClientesAdapter
import com.sistemasmcanicosjh.databinding.FragmentClientesBinding
import com.sistemasmcanicosjh.viewmodel.ClientesViewModel

class ClientesFragment : Fragment() {

    private lateinit var clientesViewModel: ClientesViewModel
    private var _binding: FragmentClientesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         clientesViewModel =
            ViewModelProvider(this).get(ClientesViewModel::class.java)

        _binding = FragmentClientesBinding.inflate(inflater, container, false)

        binding.addClientes.setOnClickListener{
            findNavController().navigate(R.id.action_nav_clientes_to_addClintesFragment)
        }
        val clientesAdapter= ClientesAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter = clientesAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())

        clientesViewModel = ViewModelProvider(this)[ClientesViewModel::class.java]

        clientesViewModel.getAllData.observe(viewLifecycleOwner){clientes ->
            clientesAdapter.setData(clientes)}

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}