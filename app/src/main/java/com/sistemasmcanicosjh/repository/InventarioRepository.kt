package com.sistemasmcanicosjh.repository

import androidx.lifecycle.LiveData
import com.sistemasmcanicosjh.data.InventarioDao
import com.sistemasmcanicosjh.model.Inventario

class InventarioRepository(private val inventarioDao: InventarioDao) {

    val getAllData : LiveData<List<Inventario>> = inventarioDao.getAllData()

    suspend fun addInventario(obj: Inventario){
        inventarioDao.addInventario(obj)
    }

    suspend fun updateInventario(obj: Inventario){
        inventarioDao.updateInventario(obj)
    }

    suspend fun deleteInventario(obj: Inventario){
        inventarioDao.deleteInventario(obj)
    }
}