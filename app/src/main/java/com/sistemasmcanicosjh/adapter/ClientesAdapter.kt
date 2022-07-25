package com.sistemasmcanicosjh.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sistemasmcanicosjh.databinding.ClientesFilaBinding
import com.sistemasmcanicosjh.databinding.FragmentAddClientesBinding
import com.sistemasmcanicosjh.model.Clientes
import com.sistemasmcanicosjh.ui.clientes.ClientesFragment
import com.sistemasmcanicosjh.ui.clientes.ClientesFragmentDirections
import com.sistemasmcanicosjh.ui.clientes.ClientesFragmentDirections.Companion.actionNavClientesToUpdateClientesFragment2
import com.sistemasmcanicosjh.ui.clientes.UpdateClientesFragmentDirections


class ClientesAdapter : RecyclerView.Adapter<ClientesAdapter.ClientesViewHolder>() {

    private var listaClientes = emptyList<Clientes>()

    inner class ClientesViewHolder(private var itemBinding: ClientesFilaBinding) :
        RecyclerView.ViewHolder(itemBinding.root){
        fun dibuja(clientes: Clientes){

            itemBinding.tvNombreCompleto.text = clientes.nombreCompleto
            itemBinding.tvCorreo.text = clientes.correo
            itemBinding.tvLatitud.text = clientes.latitud.toString()
            itemBinding.tvLongitud.text = clientes.longitud.toString()
            itemBinding.tvAltura.text = clientes.altura.toString()
            itemBinding.tvRutaImagen.text = clientes.rutaImagen
            itemBinding.btUpdate.setOnClickListener{
                val accion = ClientesFragmentDirections
                    .actionNavClientesToUpdateClientesFragment2(clientes)
                itemView.findNavController().navigate(accion)         }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientesViewHolder {
        val itemBinding = ClientesFilaBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)
        return ClientesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ClientesViewHolder, position: Int) {
        val clientes = listaClientes[position]
        holder.dibuja(clientes)
    }

    override fun getItemCount(): Int {
        return listaClientes.size
    }

    fun setData(clientes: List<Clientes>){
        this.listaClientes=clientes
        notifyDataSetChanged()
    }
}