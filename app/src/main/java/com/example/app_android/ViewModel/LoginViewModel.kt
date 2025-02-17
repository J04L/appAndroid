package com.example.app_android.ViewModel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.app_android.Model.LoginRequest
import com.example.app_android.Model.LoginResponse


class LoginViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class LoginViewModel(private val apiService: ApiService) : ViewModel() {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>() // Corrected variable name
    val password: LiveData<String> = _password // Corrected variable name

    private val _loginEnabled = MutableLiveData<Boolean>(false) // Corrected variable name
    val loginEnabled: LiveData<Boolean> = _loginEnabled // Corrected variable name

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginResult = MutableLiveData<LoginResponse?>(null)
    val loginResult: LiveData<LoginResponse?> = _loginResult

    fun onEmailChanged(email: String) {
        _email.value = email
        validateForm()
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
        validateForm()
    }

    private fun validateForm() {
        val email = _email.value.orEmpty()
        val password = _password.value.orEmpty()
        _loginEnabled.value = isValidEmail(email) && password.isNotEmpty()
    }

    private fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun login() {
        val email = _email.value.orEmpty()
        val password = _password.value.orEmpty()

        Log.d("lala","entra al login "+ email+" "+ password)
        _isLoading.value = true // Set loading to true

        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email, password)
                val response = apiService.loginApp(loginRequest)
                _loginResult.value = response
            } catch (e: Exception) {
                // Handle network errors, etc.
                _loginResult.value = LoginResponse(null, null, e.message) // Set error message
            } finally {
                _isLoading.value = false // Set loading to false
            }
        }
    }

    fun clearLoginResult() {
        _loginResult.value = null
    }
}
