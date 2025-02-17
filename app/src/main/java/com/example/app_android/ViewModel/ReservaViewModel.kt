package com.example.app_android.ViewModel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.app_android.Model.Reserva
import com.example.app_android.View.CrearReserva.Habitacion1
import java.text.SimpleDateFormat
import java.util.*

class ReservaViewModel : ViewModel() {


    // Fechas de la reserva
    var fechaInicio by mutableStateOf("")
    var fechaSalida by mutableStateOf("")

    // Número de huéspedes
    var numeroHuespedes by mutableStateOf(1)

    // Datos del huésped
    var huespedEmail by mutableStateOf("heliosstarservitor@gmail.com")
    var huespedNombre by mutableStateOf("Helios")
    var huespedApellidos by mutableStateOf("Star")
    var huespedDni by mutableStateOf("12345678T")
    // Datos del trabajador
    var trabajadorEmail by mutableStateOf("")

    // Lógica para calcular el precio de la reserva
    var precioTotal by mutableStateOf(0.0)

    // Datos de la habitación seleccionada
    var habitacionSeleccionada by mutableStateOf<Habitacion1?>(null)

    // Cuna y cama extra
    var cuna by mutableStateOf(false)
    var camaExtra by mutableStateOf(false)



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

        // Cálculo de precio total si no se proporciona
        val precioTotalCalculado = precioTotal.takeIf { it != 0.0 } ?: (habitacionSeleccionada?.precio ?: 0.0) * totalDias

        return Reserva(
            n_habitacion = (habitacionSeleccionada?.numeroHabitacion ?: "").toString(),
            tipo_habitacion = habitacionSeleccionada?.tipo ?: "",
            f_Inicio = fechaInicioDate,  // Asegúrate de que esta fecha sea un objeto Date
            f_Final = fechaFinDate,      // Lo mismo para la fecha de finalización
            totalDias = totalDias,       // Este campo se calculará automáticamente en el hook pre-save
            huespedEmail = huespedEmail,
            huespedNombre = huespedNombre,
            huespedApellidos = huespedApellidos,
            huespedDni = huespedDni,
            trabajadorEmail = trabajadorEmail,
            numeroHuespedes = numeroHuespedes,
            precio_noche = habitacionSeleccionada?.precio ?: 0.0,
            precio_total = precioTotalCalculado,  // Aquí calculamos el precio total si no se pasa como argumento
            cuna = cuna,
            camaExtra = camaExtra
        )
    }

}

// Datos de la habitación seleccionada
data class Habitacion2(
    val numeroHabitacion: String,
    val tipo: String,
    val precio: Double,
    val fotoUrl: String
)
