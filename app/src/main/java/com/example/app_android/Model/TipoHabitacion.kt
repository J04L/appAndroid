package com.example.app_android.Model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TipoHabitacion(
    @SerializedName("nombreTipo")
    val nombreTipo: String,

    @SerializedName("precioBase")
    val precioBase: Double,

    @SerializedName("capacidadCamas")
    val capacidadCamas: Int,

    @SerializedName("capacidadPersonas")
    val capacidadPersonas: CapacidadPersonas
)

@Serializable
data class CapacidadPersonas(
    @SerializedName("adultos")
    val adultos: Int,

    @SerializedName("menores")
    val menores: Int
){
    override fun toString(): String {
        return "${adultos} adultos" + if (menores > 0) " y ${menores} menores" else ""
    }
}
