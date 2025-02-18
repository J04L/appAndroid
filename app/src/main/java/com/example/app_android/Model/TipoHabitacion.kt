package com.example.app_android.Model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TipoHabitacion(
    @SerialName("nombreTipo")
    val nombreTipo: String,

    @SerialName("precioBase")
    val precioBase: Double,

    @SerialName("capacidadCamas")
    val capacidadCamas: Int,

    @SerialName("capacidadPersonas")
    val capacidadPersonas: CapacidadPersonas
)

@Serializable
data class CapacidadPersonas(
    @SerialName("adultos")
    val adultos: Int,

    @SerialName("menores")
    val menores: Int
){
    override fun toString(): String {
        return "${adultos} adultos" + if (menores > 0) " y ${menores} menores" else ""
    }
}
