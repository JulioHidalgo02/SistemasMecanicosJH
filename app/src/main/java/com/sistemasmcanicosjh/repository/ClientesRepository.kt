package com.sistemasmcanicosjh.repository

import androidx.lifecycle.MutableLiveData
import com.sistemasmcanicosjh.data.ClientesDao
import com.sistemasmcanicosjh.model.Clientes

class ClientesRepository(private val clientesDao: ClientesDao) {
    val getAllData : MutableLiveData<List<Clientes>> = clientesDao.obtenerClientes()

    fun agregarCliente(cliente: Clientes){
        clientesDao.saveCliente(cliente)
    }

    fun actualizarCliente(cliente: Clientes){
        clientesDao.saveCliente(cliente)
    }

    fun deleteCliente(cliente: Clientes){
        clientesDao.deleteCliente(cliente)
    }
}