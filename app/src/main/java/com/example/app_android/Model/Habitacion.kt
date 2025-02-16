package com.example.app_android.Model
import com.google.gson.annotations.SerializedName

data class Habitacion(
    @SerializedName("numeroHabitacion")
    val numeroHabitacion: Int,
    @SerializedName("tipoHabitacion")
    val tipoHabitacion: TipoHabitacion,
    @SerializedName("descripcion")
    val descripcion: String,
    @SerializedName("precio")
    val precio: Double,
    @SerializedName("fotos")
    val fotos: List<String>,
    @SerializedName("camas")
    val camas: Camas,
    @SerializedName("dimensiones")
    val dimensiones: Double,
    @SerializedName("disponible")
    val disponible: Boolean = true,
    @SerializedName("piso")
    val piso: Int
)

data class Camas(
    @SerializedName("individual")
    val individual: Int,
    @SerializedName("doble")
    val doble: Int
)
