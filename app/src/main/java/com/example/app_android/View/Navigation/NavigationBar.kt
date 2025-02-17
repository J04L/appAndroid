package com.example.app_android.View.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Perfil : BottomNavItem("perfil", Icons.Default.Person, "Perfil")
    object Reservas : BottomNavItem("reservas", Icons.Default.DateRange, "Reservas")
    object Reservar : BottomNavItem("reservar", Icons.Default.AddCircle, "Reservar") // Nuevo botón

    object Login : BottomNavItem("login",Icons.Default.Person,"Login")
    object Register: BottomNavItem("register",Icons.Default.Person,"Register")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Perfil,
        BottomNavItem.Reservas,
        BottomNavItem.Reservar // Añadido a la lista

    )
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Check if the current route is NOT the Login or Register route
    if (currentRoute != BottomNavItem.Login.route && currentRoute != BottomNavItem.Register.route) {
        val items = listOf(
            BottomNavItem.Perfil,
            BottomNavItem.Reservas,
        )

        NavigationBar {
            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                    label = { Text(item.title) },
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route)
                        }
                    }
                )
            }
        }
    }
}