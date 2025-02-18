package com.example.app_android.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_android.Model.Filtros
import com.example.app_android.Model.Reserva
import com.example.app_android.ViewModel.RetrofitClient
import com.example.app_android.ViewModel.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class HistorialReservaViewModel : ViewModel() {
    val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
    private val apiService = RetrofitClient.instance
    val reservasLiveData = MutableLiveData<List<Reserva>>()
    val errorLiveData = MutableLiveData<String>()

    fun obtenerReservas(email: String) {
        viewModelScope.launch {
            try {
                val filtros = Filtros(huespedEmail = email)
                val response = withContext(Dispatchers.IO) { apiService.obtenerReservas(filtros).execute() }
                if (response.isSuccessful) {
                    val reservas = response.body()?.map { reserva ->
                        Log.d("Reserva", "ID: ${reserva._id}") // Verifica el valor de _id en las reservas
                        reserva.copy(
                            // Deja las fechas como String, no las parsees
                            f_Inicio = reserva.f_Inicio,
                            f_Final = reserva.f_Final
                        )
                    } ?: emptyList()
                    reservasLiveData.postValue(reservas)
                } else {
                    errorLiveData.postValue("Error: ${response.message()}")
                }
            } catch (e: HttpException) {
                errorLiveData.postValue("Error: ${e.message()}")
            } catch (e: Exception) {
                errorLiveData.postValue("Error de red: ${e.message}")
                Log.e("HistorialReserva", "Error de red: ${e.message}")
            }
        }
    }
    data class IdRequest(
        val _id: String

    )

    fun eliminarReserva(id: String) {
        viewModelScope.launch {
            try {
                // Crear el objeto IdRequest con el _id
                val idRequest = IdRequest(id)

                val response = withContext(Dispatchers.IO) {
                    apiService.eliminarReserva(idRequest) // Enviar el objeto IdRequest al endpoint
                }

                if (response.isSuccessful) {
                    // Eliminar la reserva de la lista local si la eliminación fue exitosa
                    reservasLiveData.postValue(reservasLiveData.value?.filter { it._id != id })
                } else {
                    errorLiveData.postValue("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                errorLiveData.postValue("Error de red: ${e.message}")
            }
        }
    }


    fun actualizarReserva(reserva: Reserva) {
        // Convertir las fechas de String a Date
        val formattedFInicio = formatDate(reserva.f_Inicio.toString())
        val formattedFFinal = formatDate(reserva.f_Final.toString())

        // Convertir las fechas formateadas de String a Date
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
        val fInicioDate = dateFormat.parse(formattedFInicio.toString())
        val fFinalDate = dateFormat.parse(formattedFFinal.toString())

        // Crear una copia de la reserva con las fechas convertidas
        val updatedReserva = reserva.copy(
            f_Inicio = fInicioDate,
            f_Final = fFinalDate
        )

        // Llamar al ApiService con la reserva actualizada
        viewModelScope.launch {
            try {
                // Enviar el objeto reserva actualizado al backend
                apiService.actualizarReserva(updatedReserva)
                // Aquí puedes manejar la respuesta
            } catch (e: Exception) {
                // Manejo de errores
                errorLiveData.postValue("Error al actualizar la reserva: ${e.message}")
            }
        }
    }

    fun formatDate(dateStr: String): Date? {
        val format = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
        format.timeZone = TimeZone.getTimeZone("GMT")  // Establecer la zona horaria explícitamente

        return try {
            format.parse(dateStr)
        } catch (e: Exception) {
            e.printStackTrace()  // Mostrar detalles del error
            null
        }
    }

}


