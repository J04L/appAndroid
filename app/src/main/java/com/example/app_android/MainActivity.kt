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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_android.View.CrearReserva.ReservaScreen
import com.example.app_android.View.MenuHabitaciones.pantallaHabitaciones
import com.example.app_android.View.Navigation.BottomNavItem
import com.example.app_android.View.Navigation.BottomNavigationBar
import com.example.app_android.ViewModel.HabitacionViewModel
import com.example.app_android.ViewModel.ReservaViewModel
import com.example.app_android.ui.theme.AppandroidTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val habitacionViewModel : HabitacionViewModel by viewModels()
        val reservaViewModel: ReservaViewModel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppandroidTheme {
                val habitaciones = habitacionViewModel.Habitaciones.collectAsState()
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItem.Reservas.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(BottomNavItem.Reservas.route) { pantallaHabitaciones(habitacionViewModel) }
                        composable(BottomNavItem.Perfil.route) { Greeting("joel") }
                        composable(BottomNavItem.Reservar.route){ ReservaScreen(reservaViewModel) }
                    }
                        habitaciones.value.forEach{ habitacion ->
                            Log.d("Habitacion", (habitacion.numeroHabitacion + habitacion.fotos.count()).toString())
                        }

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