package com.example.app_android.Model

data class Filtros(
    val huespedEmail: String? = null,
    val n_habitacion: String? = null,
    val f_Inicio: String? = null,
    val f_Final: String? = null,
    val numeroHuespedes: Int? = null,
    val precioMin: Double? = null,
    val precioMax: Double? = null,
    val precioNocheMin: Double? = null,
    val precioNocheMax: Double? = null
)