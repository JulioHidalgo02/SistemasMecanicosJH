package com.sistemasmcanicosjh.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "inventario")
data class Inventario(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name="nombreObjeto")
    val nombre: String?,
    @ColumnInfo(name="marca")
    val marca: String?,
    @ColumnInfo(name="cantidadObjetos")
    val cantidad: Int?,
    @ColumnInfo(name="estado")
    val estado: String?,
    @ColumnInfo(name="ruta")
    val rutaImagen: String?
) : Parcelable
