package com.example.app_android.View.historial

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_android.Model.Reserva
import com.example.app_android.ViewModel.HistorialReservaViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.widget.EditText


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun HistorialReservasScreen(
    email: String,
    viewModel: HistorialReservaViewModel = viewModel()
) {
    val reservas by viewModel.reservasLiveData.observeAsState(initial = emptyList())
    val error by viewModel.errorLiveData.observeAsState()
    val showDialog = remember { mutableStateOf(false) }  // Estado para mostrar el dialogo de eliminación
    val showEditDialog = remember { mutableStateOf(false) }  // Estado para mostrar el dialogo de edición
    val reservaToDelete = remember { mutableStateOf<Reserva?>(null) }  // Para mantener la reserva seleccionada para eliminación
    val reservaToEdit = remember { mutableStateOf<Reserva?>(null) }  // Para mantener la reserva seleccionada para edición

    LaunchedEffect(email) {
        viewModel.obtenerReservas(email)
    }

    // Mostrar el dialogo de confirmación para eliminar
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Confirmación") },
            text = { Text("¿Estás seguro de que deseas eliminar esta reserva?") },
            confirmButton = {
                Button(
                    onClick = {
                        reservaToDelete.value?.let { reserva ->
                            viewModel.eliminarReserva(reserva._id ?: "")
                        }
                        showDialog.value = false  // Cerrar el dialogo
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false }  // Cerrar el dialogo
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Mostrar el dialogo de edición
    if (showEditDialog.value && reservaToEdit.value != null) {
        EditarReservaDialog(
            reserva = reservaToEdit.value!!,
            onUpdate = { updatedReserva ->
                // Actualizar la reserva en el ViewModel
                viewModel.actualizarReserva(updatedReserva)
                showEditDialog.value = false  // Cerrar el dialogo
            },
            onDismiss = { showEditDialog.value = false }  // Cerrar el dialogo sin cambios
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historial de Reservas") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                error != null -> {
                    Text(text = "Error: ${error}", color = MaterialTheme.colorScheme.error)
                }
                reservas.isEmpty() -> {
                    Text(text = "No hay reservas disponibles", style = MaterialTheme.typography.bodyLarge)
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(reservas) { reserva ->
                            ReservaItem(reserva, onDeleteClick = {
                                reservaToDelete.value = reserva  // Guardamos la reserva seleccionada
                                showDialog.value = true  // Mostramos el dialogo de eliminación
                            }, onEditClick = { reserva ->
                                reservaToEdit.value = reserva  // Guardamos la reserva seleccionada para editar
                                showEditDialog.value = true  // Mostramos el dialogo de edición
                            })
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun ReservaItem(reserva: Reserva, onDeleteClick: (String) -> Unit, onEditClick: (Reserva) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Habitación: ${reserva.n_habitacion}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Tipo: ${reserva.tipo_habitacion}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Inicio: ${reserva.f_Inicio}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Fin: ${reserva.f_Final}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Precio Total: ${reserva.precio_total}€", style = MaterialTheme.typography.bodyMedium)

            // Botón para editar la reserva
            Button(
                onClick = { onEditClick(reserva) }, // Llamar a la función onEditClick al presionar el botón
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Editar Reserva")
            }

            // Botón para eliminar la reserva
            Button(
                onClick = { onDeleteClick(reserva._id ?: "") },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Eliminar Reserva")
            }
        }
    }
}
@Composable
fun EditarReservaDialog(
    reserva: Reserva,
    onUpdate: (Reserva) -> Unit,
    onDismiss: () -> Unit
) {
    var nHabitacion by remember { mutableStateOf(reserva.n_habitacion) }
    var huespedEmail by remember { mutableStateOf(reserva.huespedEmail) }
    var fInicio by remember { mutableStateOf(reserva.f_Inicio) }
    var fFinal by remember { mutableStateOf(reserva.f_Final) }

    val context = LocalContext.current

    // Funciones para abrir el DatePickerDialog
    fun showDatePickerInicio() {
        val calendario = Calendar.getInstance()
        val year = calendario.get(Calendar.YEAR)
        val month = calendario.get(Calendar.MONTH)
        val dayOfMonth = calendario.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                // Actualizamos la fecha de inicio en formato yyyy-MM-dd
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                fInicio = selectedDate.time

                // Actualizamos el texto mostrando la fecha en el formato adecuado
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.show()
    }

    fun showDatePickerFinal() {
        val calendario = Calendar.getInstance()
        val year = calendario.get(Calendar.YEAR)
        val month = calendario.get(Calendar.MONTH)
        val dayOfMonth = calendario.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)

                // Actualizamos la fecha de fin en formato yyyy-MM-dd
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                fFinal = selectedDate.time
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.show()
    }

    // Mostrar el diálogo de edición dentro de una función composable
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Reserva") },
        text = {
            Column {
                TextField(
                    value = nHabitacion,
                    onValueChange = { nHabitacion = it },
                    label = { Text("Número de Habitación") }
                )
                TextField(
                    value = huespedEmail,
                    onValueChange = { huespedEmail = it },
                    label = { Text("Email del Huésped") }
                )

                // Botón para seleccionar la fecha de inicio
                Button(onClick = { showDatePickerInicio() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Seleccionar Fecha de Inicio")
                }
                // Mostrar la fecha de inicio en el formato yyyy-MM-dd
                Text("Fecha de Inicio: ${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fInicio)}", style = MaterialTheme.typography.bodyMedium)

                // Botón para seleccionar la fecha de fin
                Button(onClick = { showDatePickerFinal() }, modifier = Modifier.fillMaxWidth()) {
                    Text("Seleccionar Fecha de Fin")
                }
                // Mostrar la fecha de fin en el formato yyyy-MM-dd
                Text("Fecha de Fin: ${SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(fFinal)}", style = MaterialTheme.typography.bodyMedium)
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Al momento de actualizar, ya tenemos las fechas como objetos Date
                    val updatedReserva = reserva.copy(
                        n_habitacion = nHabitacion,
                        huespedEmail = huespedEmail,
                        f_Inicio = fInicio,  // Asignamos un objeto Date
                        f_Final = fFinal     // Asignamos un objeto Date
                    )
                    onUpdate(updatedReserva)
                }
            ) {
                Text("Actualizar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

