package com.example.app_android

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_android.View.CrearReserva.ReservaScreen
import com.example.app_android.View.MenuHabitaciones.pantallaHabitaciones
import com.example.app_android.View.Navigation.BottomNavItem
import com.example.app_android.View.Navigation.BottomNavigationBar
import com.example.app_android.ViewModel.HabitacionViewModel
import com.example.app_android.ViewModel.ReservaViewModel
import com.example.app_android.ViewModel.LoginViewModel
import com.example.app_android.ViewModel.RegisterViewModel
import com.example.app_android.ui.theme.AppandroidTheme
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.app_android.Model.TipoHabitacion
import com.example.app_android.View.historial.HistorialReservasScreen
import com.example.app_android.ViewModel.HistorialReservaViewModel
import com.example.app_android.ViewModel.LoginViewModelFactory
import com.example.app_android.ViewModel.RetrofitClient
import kotlinx.serialization.json.Json


class MainActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        val apiService = RetrofitClient.instance
        val historialReservaViewModel: HistorialReservaViewModel by viewModels()
        val habitacionViewModel : HabitacionViewModel by viewModels()
        val reservaViewModel: ReservaViewModel by viewModels()
        val loginViewModel: LoginViewModel by viewModels {
            LoginViewModelFactory(apiService)
        }
        val registerViewModel: RegisterViewModel by viewModels()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Inicializar el MediaPlayer con el archivo de música en 'raw'
        mediaPlayer = MediaPlayer.create(this, R.raw.costalow)  // Asegúrate de usar el nombre correcto de tu archivo
        mediaPlayer?.isLooping = true  // Para que la música se repita en bucle
        mediaPlayer?.start()  // Comienza la reproducción
        setContent {
            AppandroidTheme {
                val habitaciones = habitacionViewModel.Habitaciones.collectAsState()

                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItem.Habitaciones.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(BottomNavItem.Habitaciones.route) {
                            pantallaHabitaciones(
                                habitacionViewModel, navController
                            )
                        }
                        composable(BottomNavItem.Historial.route) {
                            HistorialReservasScreen(email = "heliosstarservitor@gmail.com", viewModel = historialReservaViewModel)
                        }
                        composable(BottomNavItem.Perfil.route) { Greeting("joel") }
                        composable(BottomNavItem.Reservar.route) {ReservaScreen(reservaViewModel) }

                    }
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()  // Detiene la música
        mediaPlayer?.release()  // Libera los recursos
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