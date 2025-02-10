package com.example.app_android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.app_android.View.MenuHabitaciones.pantallaHabitaciones
import com.example.app_android.ViewModel.HabitacionViewModel
import com.example.app_android.ui.theme.AppandroidTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val habitacionViewModel : HabitacionViewModel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppandroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    val listaTipoHabitaciones = habitacionViewModel.tipoHabitaciones.collectAsState().value
                    pantallaHabitaciones(listaTipoHabitaciones)
                }
            }
        }
        lifecycleScope.launch{
            habitacionViewModel.tipoHabitaciones.collectLatest { lista ->
                for((nombre, valor) in lista){
                    Log.d("hola", nombre + valor.precioBase)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppandroidTheme {
        Greeting("Android")
    }
}