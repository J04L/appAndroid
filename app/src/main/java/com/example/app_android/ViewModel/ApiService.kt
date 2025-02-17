package com.example.app_android.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.app_android.Model.Habitacion
import com.example.app_android.Model.LoginRequest
import com.example.app_android.Model.LoginResponse
import com.example.app_android.Model.RegisterRequest
import com.example.app_android.Model.RegisterResponse
import com.example.app_android.Model.TipoHabitacion
import com.example.app_android.Model.Reserva  // Asegúrate de tener la clase Reserva en tu modelo
import kotlinx.coroutines.flow.Flow
import okhttp3.Response
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("tipoHabitaciones")
    suspend fun obtenerTipoHabitaciones(): List<TipoHabitacion>

    @GET("habitacion")
    suspend fun obtenerHabitaciones(): List<Habitacion>

    // Nueva función para hacer la reserva
    @POST("reservas")
    suspend fun hacerReserva(@Body reserva: Reserva): Reserva

    @POST("reservas/new")
    fun crearReserva(@Body reserva: Reserva): Reserva
    @POST("usuarios/loginApp")
    suspend fun loginApp(@Body loginRequest: LoginRequest): LoginResponse

    @POST("usuarios/registerUser")
    suspend fun newUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse> // Correct: Response<RegisterResponse>
}
