package com.example.app_android.ViewModel

import com.example.app_android.Model.Habitacion
import com.example.app_android.Model.LoginRequest
import com.example.app_android.Model.LoginResponse
import com.example.app_android.Model.RegisterRequest
import com.example.app_android.Model.RegisterResponse
import com.example.app_android.Model.TipoHabitacion
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("tipoHabitaciones")
    suspend fun obtenerTipoHabitaciones(): List<TipoHabitacion>

    @GET("habitacion")
    suspend fun obtenerHabitaciones(): List<Habitacion>

    @POST("usuarios/loginApp")
    suspend fun loginApp(@Body loginRequest: LoginRequest): LoginResponse

    @POST("usuarios/registerUser")
    suspend fun newUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse> // Correct: Response<RegisterResponse>
}
