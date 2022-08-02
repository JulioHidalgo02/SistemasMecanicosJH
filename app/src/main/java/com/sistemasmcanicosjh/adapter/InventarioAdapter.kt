package com.sistemasmcanicosjh.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sistemasmcanicosjh.databinding.InventarioFilaBinding
import com.sistemasmcanicosjh.databinding.FragmentAddInventarioBinding
import com.sistemasmcanicosjh.model.Inventario
import com.sistemasmcanicosjh.ui.inventario.InventarioFragment
import com.sistemasmcanicosjh.ui.inventario.InventarioFragmentDirections
import com.sistemasmcanicosjh.ui.inventario.InventarioFragmentDirections.Companion.actionNavInventarioToUpdateInventarioFragment2
import com.sistemasmcanicosjh.ui.inventario.UpdateInventarioFragmentDirections


class InventarioAdapter : RecyclerView.Adapter<InventarioAdapter.InventarioViewHolder>() {

    private var listaInventario = emptyList<Inventario>()

    inner class InventarioViewHolder(private var itemBinding: InventarioFilaBinding) :
        RecyclerView.ViewHolder(itemBinding.root){
        fun dibuja(inventario: Inventario){

            itemBinding.tvNombreObjeto.text = inventario.nombre
            itemBinding.tvEstado.text = inventario.estado
            itemBinding.tvMarca.text = inventario.marca
            itemBinding.tvCantidadObjetos.text = inventario.cantidad.toString()
            Glide.with(itemBinding.root.context)
                .load(inventario.rutaImagen)
                .circleCrop()
                .into(itemBinding.ImageId)
            itemBinding.btUpdate.setOnClickListener{
                val accion = InventarioFragmentDirections
                    .actionNavInventarioToUpdateInventarioFragment2(inventario)
                itemView.findNavController().navigate(accion)         }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventarioViewHolder {
        val itemBinding = InventarioFilaBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)
        return InventarioViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: InventarioViewHolder, position: Int) {
        val inventario = listaInventario[position]
        holder.dibuja(inventario)
    }

    override fun getItemCount(): Int {
        return listaInventario.size
    }

    fun setData(inventario: List<Inventario>){
        this.listaInventario=inventario
        notifyDataSetChanged()
    }
}