package com.sistemasmcanicosjh.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.sistemasmcanicosjh.data.InventarioDatabase
import com.sistemasmcanicosjh.model.Inventario
import com.sistemasmcanicosjh.repository.InventarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class InventarioViewModel(application: Application) : AndroidViewModel(application) {
    val getAllData : LiveData<List<Inventario>>

    private val repository : InventarioRepository

    init {
        val inventarioDao = InventarioDatabase.getDatabase(application).inventarioDao()
        repository = InventarioRepository(inventarioDao)
        getAllData = repository.getAllData
    }

    fun addInventario(obj : Inventario){
        viewModelScope.launch(Dispatchers.IO){ repository.addInventario(obj)}
    }

    fun updateInventario (obj: Inventario){
        viewModelScope.launch(Dispatchers.IO){ repository.updateInventario(obj)}
    }

    fun deleteInventario (obj: Inventario){
        viewModelScope.launch(Dispatchers.IO) { repository.deleteInventario(obj) }
    }

}