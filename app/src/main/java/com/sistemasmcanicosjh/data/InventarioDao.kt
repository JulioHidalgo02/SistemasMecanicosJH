package com.sistemasmcanicosjh.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sistemasmcanicosjh.model.Inventario

@Dao
interface InventarioDao {
    @Query("SELECT * FROM INVENTARIO")
    fun getAllData() : LiveData<List<Inventario>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addInventario(obj: Inventario)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateInventario(obj: Inventario)

    @Delete
    fun deleteInventario(obj: Inventario)

}