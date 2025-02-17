package com.example.app_android.ViewModel

import com.example.app_android.Model.User

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_android.Model.RegisterRequest
import com.example.app_android.Model.RegisterResponse
import com.example.app_android.ViewModel.RetrofitClient
import com.example.app_android.ViewModel.ApiService
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _registrationResult = MutableLiveData<Result<RegisterResponse>>() // Cambia a RegisterResponse
    val registrationResult: LiveData<Result<RegisterResponse>>
        get() = _registrationResult

    fun registerUser(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            try {
                val response: Response<RegisterResponse> = RetrofitClient.instance.newUser(registerRequest) // Usa la instancia singleton

                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    _registrationResult.value = Result.success(registerResponse!!) // !! para asegurar que no es nulo (manejar posible null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = if (errorBody != null) {
                        "Error en el registro: $errorBody"
                    } else {
                        "Error desconocido en el registro: ${response.code()}"
                    }
                    _registrationResult.value = Result.failure(Exception(errorMessage))
                }
            } catch (e: Exception) {
                _registrationResult.value = Result.failure(e)
            }
        }
    }
}
