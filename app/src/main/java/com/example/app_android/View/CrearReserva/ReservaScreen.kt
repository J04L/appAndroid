package com.example.app_android.View.CrearReserva

import com.example.app_android.ViewModel.ReservaViewModel
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.example.app_android.ViewModel.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale

@Composable
fun ReservaScreen(viewModel: ReservaViewModel) {
    val context = LocalContext.current
    var habitacionesDisponibles by remember { mutableStateOf<List<Habitacion1>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        @Composable
        fun HuespedesDropdown(
            selectedHuespedes: Int,
            onHuespedesSelected: (Int) -> Unit
        ) {
            var expanded by remember { mutableStateOf(false) }
            val opciones = (1..6).toList() // Permite seleccionar de 1 a 6 huéspedes

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.CenterStart)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4))
                ) {
                    Text(text = "Huéspedes: $selectedHuespedes", color = Color.White)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    opciones.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(text = opcion.toString()) },
                            onClick = {
                                onHuespedesSelected(opcion)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        Text(text = "Reserva tu estancia", fontSize = 22.sp)

        // 📅 Fecha de Inicio
        FechaSelector("Fecha de inicio", viewModel.fechaInicio) {
            showDatePicker(context) { date -> viewModel.fechaInicio = date }
        }

        // 📅 Fecha de Salida
        FechaSelector("Fecha de salida", viewModel.fechaSalida) {
            showDatePicker(context) { date -> viewModel.fechaSalida = date }
        }

        // 👤 Selección de huéspedes
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Número de huéspedes", fontSize = 18.sp)
                HuespedesDropdown(viewModel.numeroHuespedes) { viewModel.numeroHuespedes = it }
            }
        }

        // Botón de confirmación
        Button(
            onClick = {
                coroutineScope.launch {
                    isLoading = true
                    errorMessage = null
                    val resultado = buscarHabitaciones(
                        capacidad = viewModel.numeroHuespedes,
                        fechaInicio = viewModel.fechaInicio,
                        fechaFin = viewModel.fechaSalida
                    )
                    isLoading = false
                    if (resultado.isSuccess) {
                        habitacionesDisponibles = resultado.getOrDefault(emptyList())
                    } else {
                        errorMessage = resultado.exceptionOrNull()?.message
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text(text = "Confirmar Reserva", fontSize = 18.sp, color = Color.White)
        }

        // 🔄 Mostrar estado de carga
        if (isLoading) {
            CircularProgressIndicator()
        }

        // ❌ Mostrar error si ocurre
        errorMessage?.let {
            Text(text = "Error: $it", color = Color.Red)
        }

        // Mostrar lista de habitaciones disponibles
        if (habitacionesDisponibles.isNotEmpty()) {
            Text(text = "Habitaciones disponibles:", fontSize = 20.sp)
            LazyColumn {
                items(habitacionesDisponibles) { habitacion ->
                    HabitacionItem(
                        habitacion = habitacion,
                        viewModel = viewModel,
                        onReservaConfirmada = {
                            // Eliminar todas las habitaciones disponibles después de hacer la reserva
                            habitacionesDisponibles = emptyList()
                        }
                    )
                }
            }
        }

        // Mostrar estado de carga y errores
        if (isLoading) {
            CircularProgressIndicator()
        }

        errorMessage?.let {
            Text(text = "Error: $it", color = Color.Red)
        }
    }
}

//  Función para hacer la petición al servidor

suspend fun buscarHabitaciones(
    capacidad: Int,
    fechaInicio: String,
    fechaFin: String
): Result<List<Habitacion1>> {
    return withContext(Dispatchers.IO) {
        try {
            // Determinar si es necesario formatear la fecha
            val fechaInicioStr: String
            val fechaFinStr: String

            if (fechaInicio.contains("-")) { // Si tiene guiones, es yyyy-MM-dd
                val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

                val fechaInicioFormat = inputDateFormat.parse(fechaInicio.trim())
                val fechaFinFormat = inputDateFormat.parse(fechaFin.trim())

                fechaInicioStr = outputDateFormat.format(fechaInicioFormat!!)
                fechaFinStr = outputDateFormat.format(fechaFinFormat!!)
            } else {
                // Si ya viene en yyyy/MM/dd, usarla directamente
                fechaInicioStr = fechaInicio.trim()
                fechaFinStr = fechaFin.trim()
            }

            Log.d("Reserva", "Fecha formateada: $fechaInicioStr - $fechaFinStr")

            val url = URL("http://10.0.2.2:3036/api/reservas/habitaciones/libres")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            val jsonBody = JSONObject().apply {
                put("capacidad", capacidad)
                put("fecha_inicio", fechaInicioStr)
                put("fecha_fin", fechaFinStr)
            }

            Log.d("Reserva", "JSON enviado: $jsonBody")

            connection.outputStream.use { os ->
                os.write(jsonBody.toString().toByteArray())
            }

            if (connection.responseCode == 200) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("Reserva", "Respuesta del servidor: $response")
                val habitaciones = parsearHabitaciones(response)
                return@withContext Result.success(habitaciones)
            } else {
                val errorResponse = connection.errorStream?.bufferedReader()?.use { it.readText() }
                Log.e("Reserva", "Error en el servidor: $errorResponse")
                return@withContext Result.failure(Exception("Error en la solicitud"))
            }
        } catch (e: Exception) {
            Log.e("Reserva", "Excepción en la solicitud: ${e.message}")
            return@withContext Result.failure(e)
        }
    }
}




// 📅 Selector de fecha con botón
@Composable
fun FechaSelector(label: String, fecha: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, fontSize = 18.sp)
        Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03A9F4))) {
            Text(text = if (fecha.isEmpty()) "Seleccionar fecha" else fecha, color = Color.White)
        }
    }
}

// 📅 Mostrar DatePicker
fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val fecha = "$dayOfMonth/${month + 1}/$year"
            onDateSelected(fecha)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

// 🏨 Modelo de Habitación actualizado
data class Habitacion1(
    val numeroHabitacion: Int,
    val tipo: String,
    val precio: Double,
    val fotoUrl: String // ✅ Nueva propiedad para la primera foto
)

// ✅ Función corregida para parsear JSON correctamente
fun parsearHabitaciones(json: String): List<Habitacion1> {
    val jsonArray = JSONArray(json)
    val lista = mutableListOf<Habitacion1>()

    for (i in 0 until jsonArray.length()) {
        val obj = jsonArray.getJSONObject(i)

        val tipoHabitacion = obj.getJSONObject("tipoHabitacion").getString("nombreTipo")
        val precio = obj.getDouble("precio")

        // ✅ Obtener la primera foto si existe
        val fotosArray = obj.optJSONArray("fotos")
        val primeraFoto = if (fotosArray != null && fotosArray.length() > 0) {
            "http://10.0.2.2:3036/" + fotosArray.getString(0) // Asegurar URL completa
        } else {
            "" // O una imagen por defecto
        }

        lista.add(

            Habitacion1(
                numeroHabitacion = obj.getInt("numeroHabitacion"),
                tipo = tipoHabitacion,
                precio = precio,
                fotoUrl = primeraFoto
            )
        )
    }
    return lista
}


@Composable
fun ConfirmacionReservaDialog(
    openDialog: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    habitacion: Habitacion1
) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Confirmar Reserva") },
            text = {
                Column {
                    Text("¿Estás seguro de que quieres reservar esta habitación?")
                    Text("Número de habitación: ${habitacion.numeroHabitacion}")
                    Text("Tipo: ${habitacion.tipo}")
                    Text("Precio: $${habitacion.precio}")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        )
    }
}
@Composable
fun HabitacionItem(
    habitacion: Habitacion1,
    viewModel: ReservaViewModel,
    onReservaConfirmada: () -> Unit // Callback para cuando se confirme la reserva
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = habitacion.fotoUrl,
                contentDescription = "Imagen de la habitación",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Tipo: ${habitacion.tipo}", fontSize = 18.sp)
            Text(text = "Precio: $${habitacion.precio}", fontSize = 16.sp)

            Button(
                onClick = {
                    // Al hacer clic, se selecciona esta habitación
                    viewModel.habitacionSeleccionada = habitacion
                    val reserva = viewModel.crearReserva()

                    coroutineScope.launch {
                        try {
                            val response = RetrofitClient.instance.crearReserva(reserva)
                            if (response != null) {
                                // La reserva fue exitosa
                                Toast.makeText(context, "Reserva realizada con éxito", Toast.LENGTH_SHORT).show()

                                // Llamamos a la función para limpiar la lista de habitaciones
                                onReservaConfirmada()
                            } else {
                                // Error en la respuesta
                                Toast.makeText(context, "Error al realizar la reserva", Toast.LENGTH_SHORT).show()
                            }

                        } catch (e: Exception) {
                            // Manejar errores en la llamada, como problemas de conexión
                            Log.e("Reserva", "Excepción al crear la reserva: ${e.message}")
                        }
                    }
                }
            ) {
                Text(text = "Reservar esta habitación")
            }
        }
    }
}

