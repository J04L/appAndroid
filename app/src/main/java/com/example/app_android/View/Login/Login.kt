package com.example.app_android.View.Login


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app_android.R
import com.example.app_android.View.Register.ButtonLogin
import com.example.app_android.View.Register.ImageLogin
import com.example.app_android.View.Register.OutlinedTextFieldEmail
import com.example.app_android.View.Register.OutlinedTextFieldPassword
import com.example.app_android.View.Register.SpacerLogin
import com.example.app_android.View.Register.TextLogin
import com.example.app_android.ViewModel.LoginViewModel
import kotlinx.coroutines.launch



@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("") // Corrected variable name
    val isLoading by viewModel.isLoading.observeAsState(false)
    val loginResult by viewModel.loginResult.observeAsState(null) // Observe login result

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageLogin()
        TextLogin()
        SpacerLogin()
        OutlinedTextFieldEmail(email) { viewModel.onEmailChanged(it) } // Call specific email update
        SpacerLogin()
        OutlinedTextFieldPassword(password) { viewModel.onPasswordChanged(it) } // Call specific password update
        SpacerLogin()
        ButtonLogin() { // Enable button based on validation
            coroutineScope.launch {
                viewModel.login() // Call the login function in the ViewModel
            }
        }
        if (isLoading) {
            CircularProgressIndicator() // Show progress indicator while loading
        }
        SpacerLogin()
    }

    @Composable
    fun ImageLogin() {
        Image(
            painter = painterResource(id = R.drawable.loginimage),
            contentDescription = "Login imagen"
        )
    }

    @Composable
    fun TextLogin() {
        Text(
            text = "Acceda a su cuenta",
            fontSize = 20.sp

        )
    }

    @Composable
    fun SpacerLogin() {
        Spacer(modifier = Modifier.height(10.dp))
    }

    @Composable
    fun OutlinedTextFieldEmail(email: String, onChange: (String) -> Unit) {
        OutlinedTextField(
            value = email, onValueChange = {
                onChange(it)
            }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
    }

    @Composable
    fun OutlinedTextFieldPassword(password: String, onChange: (String) -> Unit) {
        OutlinedTextField(value = password, onValueChange = {
            onChange(it)
        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))
    }

    @Composable
    fun ButtonLogin(enabled: Boolean, f: () -> Unit) {
        Button(
            onClick = f,
            enabled = enabled // Enable or disable the button
        ) {
            Text(text = "Login")
        }

    }


    // Handle login result
    LaunchedEffect(loginResult) {
        loginResult?.let { result ->
            if (result.user != null) {
                // Successful login, navigate to the next screen
                navController.navigate("habitaciones") { // Use the correct route name
                    popUpTo("login") { inclusive = true } // Prevent going back to login
                }
            } else {
                // Show error message
                Toast.makeText(context, result.errorMessage ?: "Login failed", Toast.LENGTH_SHORT).show()
            }
            viewModel.clearLoginResult() // Clear the result to avoid showing it again
        }
    }
}