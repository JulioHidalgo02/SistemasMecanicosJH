package com.sistemasmcanicosjh.ui.inventario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sistemasmcanicosjh.R
import com.sistemasmcanicosjh.adapter.InventarioAdapter
import com.sistemasmcanicosjh.databinding.FragmentInventarioBinding
import com.sistemasmcanicosjh.viewmodel.InventarioViewModel

class InventarioFragment : Fragment() {

    private lateinit var inventarioViewModel: InventarioViewModel
    private var _binding: FragmentInventarioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         inventarioViewModel =
            ViewModelProvider(this).get(InventarioViewModel::class.java)

        _binding = FragmentInventarioBinding.inflate(inflater, container, false)

        binding.addInventario.setOnClickListener{
            findNavController().navigate(R.id.action_nav_inventario_to_addInventarioFragment)
        }
        val inventarioAdapter= InventarioAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter = inventarioAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())

        inventarioViewModel = ViewModelProvider(this)[InventarioViewModel::class.java]

        inventarioViewModel.getAllData.observe(viewLifecycleOwner){inventario ->
            inventarioAdapter.setData(inventario)}

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}