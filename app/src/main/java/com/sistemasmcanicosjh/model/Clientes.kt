package com.sistemasmcanicosjh.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Clientes(
    var id: String,
    val nombreCompleto: String,
    val correo: String?,
    val latitud: Double?,
    val longitud: Double?,
    val altura: Double?,
    val rutaImagen: String?
) : Parcelable{
    constructor():
            this("","","",0.0,0.0,0.0,"")
}
