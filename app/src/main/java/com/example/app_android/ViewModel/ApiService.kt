package com.example.app_android.ViewModel

import kotlinx.coroutines.flow.Flow

interface ApiService {
    suspend fun obtenerTipoHabitaciones() : Flow<List<>>
}