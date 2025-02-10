package com.example.app_android.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_android.Model.TipoHabitacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HabitacionViewModel : ViewModel() {
    private val _tipoHabitaciones = MutableStateFlow<MutableMap<String, TipoHabitacion>>(mutableMapOf())
    val tipoHabitaciones: StateFlow<MutableMap<String, TipoHabitacion>> = _tipoHabitaciones

    init {
        obtenerTipoHabitaciones()
    }

    private fun obtenerTipoHabitaciones() {
        viewModelScope.launch {
            try {
                val lista = RetrofitClient.instance.obtenerTipoHabitaciones()

                // Crear una copia del mapa y modificarlo
                val nuevoMapa = _tipoHabitaciones.value.toMutableMap()
                lista.forEach { tipo ->
                    nuevoMapa[tipo.nombreTipo] = tipo
                }

                // Asignar el nuevo mapa para actualizar el StateFlow
                _tipoHabitaciones.value = nuevoMapa
            } catch (e: Exception) {
                _tipoHabitaciones.value = mutableMapOf() // Manejamos errores correctamente
            }
        }
    }

}