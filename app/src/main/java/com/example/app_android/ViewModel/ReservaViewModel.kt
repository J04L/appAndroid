package com.example.app_android.ViewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.app_android.Model.Reserva
import java.text.SimpleDateFormat
import java.util.*

class ReservaViewModel : ViewModel() {


    // Fechas de la reserva
    var fechaInicio by mutableStateOf("")
    var fechaSalida by mutableStateOf("")

    // Número de huéspedes
    var numeroHuespedes by mutableStateOf(1)

    // Datos del huésped
    var huespedEmail by mutableStateOf("heliosstarservitor@gamail.com")
    var huespedNombre by mutableStateOf("Helios")
    var huespedApellidos by mutableStateOf("Star")
    var huespedDni by mutableStateOf("12345678T")
    // Datos del trabajador
    var trabajadorEmail by mutableStateOf("")

    // Lógica para calcular el precio de la reserva
    var precioTotal by mutableStateOf(0.0)

    // Datos de la habitación seleccionada
    var habitacionSeleccionada by mutableStateOf<Habitacion?>(null)

    // Cuna y cama extra
    var cuna by mutableStateOf(false)
    var camaExtra by mutableStateOf(false)

    // Función para calcular el precio total de la reserva
    fun calcularPrecioTotal(): Double {
        val diasEstancia = calcularDias(fechaInicio, fechaSalida)
        return habitacionSeleccionada?.precio?.times(diasEstancia) ?: 0.0
    }

    // Método para calcular la cantidad de días entre la fecha de inicio y fin
    private fun calcularDias(fechaInicio: String, fechaFin: String): Int {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val startDate = sdf.parse(fechaInicio)
        val endDate = sdf.parse(fechaFin)
        val diffInMillis = endDate.time - startDate.time
        return (diffInMillis / (1000 * 60 * 60 * 24)).toInt()
    }

    // Función para crear la reserva
    fun crearReserva(): Reserva {
        val fechaInicioDate = SimpleDateFormat("dd/MM/yyyy").parse(fechaInicio)
        val fechaFinDate = SimpleDateFormat("dd/MM/yyyy").parse(fechaSalida)
        val totalDias = calcularDias(fechaInicio, fechaSalida)

        return Reserva(
            numeroHabitacion = habitacionSeleccionada?.numeroHabitacion ?: "",
            tipoHabitacion = habitacionSeleccionada?.tipo ?: "",
            fechaInicio = fechaInicioDate,
            fechaFin = fechaFinDate,
            totalDias = totalDias,
            huespedEmail = huespedEmail,
            huespedNombre = huespedNombre,
            huespedApellidos = huespedApellidos,
            huespedDni = huespedDni,
            trabajadorEmail = trabajadorEmail,
            numeroHuespedes = numeroHuespedes,
            precioNoche = habitacionSeleccionada?.precio ?: 0.0,
            precioTotal = precioTotal,
            cuna = cuna,
            camaExtra = camaExtra
        )
    }
}

// Datos de la habitación seleccionada
data class Habitacion(
    val numeroHabitacion: String,
    val tipo: String,
    val precio: Double,
    val fotoUrl: String
)
