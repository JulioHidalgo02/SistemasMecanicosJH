package com.sistemasmcanicosjh.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sistemasmcanicosjh.data.ClientesDao
import com.sistemasmcanicosjh.model.Clientes
import com.sistemasmcanicosjh.repository.ClientesRepository

class ClientesViewModel(application: Application)
    :AndroidViewModel(application){
        val getAllData : MutableLiveData<List<Clientes>>

        private val repository: ClientesRepository = ClientesRepository(ClientesDao())
    init {
        getAllData = repository.getAllData
    }
    fun agregarCliente(cliente: Clientes){
        repository.agregarCliente(cliente)
    }
    fun actualizarCliente(cliente: Clientes){
        repository.actualizarCliente(cliente)
    }
    fun deleteCliente(cliente: Clientes){
        repository.deleteCliente(cliente)
    }
}