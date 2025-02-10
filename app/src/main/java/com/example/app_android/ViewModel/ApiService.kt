package com.example.app_android.ViewModel

import com.example.app_android.Model.TipoHabitacion
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ApiService {
    @GET("tipoHabitaciones")
    suspend fun obtenerTipoHabitaciones() : List<TipoHabitacion>
}