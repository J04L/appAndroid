package com.example.app_android.Model

import java.util.*

data class Reserva(
    val id: String? = null, // MongoDB _id
    val n_habitacion: String,
    val tipo_habitacion: String,
    val f_Inicio: Date,
    val f_Final: Date,
    val totalDias: Int,
    val huespedEmail: String,
    val huespedNombre: String,
    val huespedApellidos: String,
    val huespedDni: String,
    val trabajadorEmail: String,
    val numeroHuespedes: Int,
    val precio_noche: Double,
    val precio_total: Double,
    val cuna: Boolean,
    val camaExtra: Boolean
)
