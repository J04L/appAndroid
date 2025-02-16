package com.example.app_android.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_android.Model.Habitacion
import com.example.app_android.Model.TipoHabitacion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HabitacionViewModel : ViewModel() {
    private val _tipoHabitaciones = MutableStateFlow<List<TipoHabitacion>>(emptyList())
    val tipoHabitaciones: StateFlow<List<TipoHabitacion>> = _tipoHabitaciones

    private val _habitaciones = MutableStateFlow<MutableList<Habitacion>>(mutableListOf())
    val Habitaciones: StateFlow<MutableList<Habitacion>> = _habitaciones

    init {
        obtenerTipoHabitaciones()
        obtenerHabitaciones()
    }

    private fun obtenerTipoHabitaciones() {
        viewModelScope.launch {
            try {
                // Asignar el nuevo mapa para actualizar el StateFlow
                _tipoHabitaciones.value = RetrofitClient.instance.obtenerTipoHabitaciones()
            } catch (e: Exception) {
                _tipoHabitaciones.value = emptyList() // Manejamos errores correctamente
            }
        }
    }
    private fun obtenerHabitaciones(){
        viewModelScope.launch{
            try{
                _habitaciones.value = RetrofitClient.instance.obtenerHabitaciones().toMutableList()
                Log.d("hola", _habitaciones.value[0].numeroHabitacion.toString())
            }catch (e: Exception){
                _habitaciones.value = mutableListOf()
            }
        }
    }

}