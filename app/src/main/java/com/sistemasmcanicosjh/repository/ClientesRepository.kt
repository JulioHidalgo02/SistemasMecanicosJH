package com.sistemasmcanicosjh.repository

import androidx.lifecycle.MutableLiveData
import com.sistemasmcanicosjh.data.ClientesDao
import com.sistemasmcanicosjh.model.Clientes

class ClientesRepository(private val clientesDao: ClientesDao) {
    val getAllData : MutableLiveData<List<Clientes>> = clientesDao.obtenerClientes()

    fun addClientes(cliente: Clientes){
        clientesDao.saveCliente(cliente)
    }

    fun updateClientes(cliente: Clientes){
        clientesDao.saveCliente(cliente)
    }

    fun deleteClientes(cliente: Clientes){
        clientesDao.deleteCliente(cliente)
    }
}