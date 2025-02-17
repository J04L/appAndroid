package com.example.app_android.Model

import java.util.*

data class Reserva(
    val id: String? = null, // MongoDB _id
    val numeroHabitacion: String,
    val tipoHabitacion: String,
    val fechaInicio: Date,
    val fechaFin: Date,
    val totalDias: Int,
    val huespedEmail: String,
    val huespedNombre: String,
    val huespedApellidos: String,
    val huespedDni: String,
    val trabajadorEmail: String,
    val numeroHuespedes: Int,
    val precioNoche: Double,
    val precioTotal: Double,
    val cuna: Boolean,
    val camaExtra: Boolean
)
