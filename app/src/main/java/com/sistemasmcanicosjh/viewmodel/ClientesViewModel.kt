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
    fun addClientes(cliente: Clientes){
        repository.addClientes(cliente)
    }
    fun updateClientes(cliente: Clientes){
        repository.updateClientes(cliente)
    }
    fun deleteClientes(cliente: Clientes){
        repository.deleteClientes(cliente)
    }
}