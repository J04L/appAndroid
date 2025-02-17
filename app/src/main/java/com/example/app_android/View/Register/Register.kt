package com.example.app_android.View.Register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.app_android.R
import com.example.app_android.ViewModel.LoginViewModel
import com.example.app_android.ViewModel.RegisterViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavHostController, viewModel: RegisterViewModel){


}

@Composable
fun ImageLogin() {
    Image(painter = painterResource(id= R.drawable.loginimage), contentDescription = "Login imagen")
}

@Composable
fun TextLogin(){
    Text(
        text = "Acceda a su cuenta",
        fontSize = 20.sp

    )
}

@Composable
fun SpacerLogin(){
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun OutlinedTextFieldEmail(email: String,onChange:(String) -> Unit){
    OutlinedTextField(value = email, onValueChange = {
        onChange(it)
    }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
fun OutlinedTextFieldPassword(password: String,onChange:(String) -> Unit){
    OutlinedTextField(value = password, onValueChange = {
        onChange(it)
    }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))
}

@Composable
fun ButtonLogin(f: ()-> Unit){
    Button(
        onClick = {
            f()
        }
    ) {
        Text(text = "Login")
    }
}