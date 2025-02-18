package com.example.app_android.View.MenuHabitaciones

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.app_android.ViewModel.HabitacionViewModel
import kotlinx.coroutines.delay







@Composable
fun pantallaHabitaciones(viewModel: HabitacionViewModel){
    var lista = viewModel.tipoHabitaciones.collectAsState().value;
    BoxWithConstraints(){
        var someBoxIsSelected by remember { mutableStateOf(false) }
        var selectedBox by remember { mutableStateOf("") }
        var listaVisibles by remember { mutableStateOf<MutableMap<String, Float>>(mutableMapOf(
            "Suite" to 1f,
            "Triple" to 1f,
            "Familiar" to 1f,
            "Doble" to 1f,
            "Individual" to 1f)) }

        LaunchedEffect(someBoxIsSelected) {
            if (!someBoxIsSelected) return@LaunchedEffect

            listaVisibles.forEach{ box ->
                if(box.key != selectedBox){
                    delay(125)
                    listaVisibles = listaVisibles.mapValues { (key, value) ->
                        if(key == box.key) 0f else value
                    }.toMutableMap()
                }
            }
        }

        val foreground = Color.White
        val fontTitleSize = 33.sp
        val fontSize = 20.sp
        val borderColor = Color.Transparent
        val backgroundTransparet = Color.Black.copy(alpha = 0.65F)
        val paddingBox = 5.dp
        val baseUrl = "http://10.0.2.2:3036/img/habitaciones"
        Column {
            //habitacion suite
            Box(modifier = Modifier
                .weight(0.4f)
                .clickable(onClick = {
                    someBoxIsSelected = true
                    selectedBox = "Suite"
                })
                .fillMaxWidth()
                .alpha(listaVisibles["Suite"]?.toFloat() ?: 0f)
                //.clip(RoundedCornerShape(30.dp))
                .padding(paddingBox)){
                AsyncImage(
                    model = "$baseUrl/habSuite/vistaEntrada.jpg", // URL de la imagen
                    contentDescription = "Imagen cargada desde URL",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .background(backgroundTransparet)
                        .padding(15.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.End
                ){
                    Text( text = "Suite" , color = foreground, fontSize = fontTitleSize)
                    Text(text = "desde " + lista.find { tipo -> tipo.nombreTipo == "Suite" }?.precioBase.toString() + "€",
                        color = foreground, fontSize = fontSize)
                }
            }
            Row (modifier = Modifier.weight(1f)){
                Column (modifier = Modifier.width(this@BoxWithConstraints.maxWidth*0.5f)){
                    //habitacion triple
                    Box(modifier = Modifier
                        .weight(0.5f)
                        .clickable(onClick = {
                            someBoxIsSelected = true
                            selectedBox = "Triple"
                        })
                        .fillMaxWidth()
                        .alpha(listaVisibles["Triple"]?.toFloat() ?: 0f)
                        .padding(paddingBox)){
                        AsyncImage(
                            model = "$baseUrl/habTriple/vistaEntrada.jpg", // URL de la imagen
                            contentDescription = "Imagen cargada desde URL",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .background(backgroundTransparet)
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(15.dp)
                            ,
                            horizontalAlignment = Alignment.Start
                        ){
                            Text( text = "Triple" , color = foreground, fontSize = fontTitleSize)
                            Text(text = "desde " + lista.find { tipo -> tipo.nombreTipo == "Triple" }?.precioBase.toString() + "€"
                                , color = foreground, fontSize = fontSize)
                        }
                    }
                    //habitacion familiar
                    Box(modifier = Modifier
                        .weight(0.75f)
                        .clickable(onClick = {
                            someBoxIsSelected = true
                            selectedBox = "Familiar"
                        })
                        .fillMaxWidth()
                        .alpha(listaVisibles["Familiar"]?.toFloat() ?: 0f)
                        .padding(paddingBox)) {
                        AsyncImage(
                            model = "$baseUrl/habFamiliar/vistaBano2.jpg", // URL de la imagen
                            contentDescription = "Imagen cargada desde URL",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .background(backgroundTransparet)
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(15.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(text = "Familiar", color = foreground, fontSize = fontTitleSize)
                            Text(text = "desde " + lista.find { tipo -> tipo.nombreTipo == "Familiar" }?.precioBase.toString() + "€",
                                 color = foreground, fontSize=fontSize)
                        }
                    }
                }
                Column (modifier = Modifier.width(this@BoxWithConstraints.maxWidth*0.5f)){
                    //celda habitacion Doble
                    Box(modifier = Modifier
                        .weight(1.5f)
                        .clickable(onClick = {
                            someBoxIsSelected = true
                            selectedBox = "Doble"
                        })
                        .fillMaxWidth()
                        .alpha(listaVisibles["Doble"]?.toFloat() ?: 0f)
                        .padding(paddingBox)) {
                        AsyncImage(
                            model = "$baseUrl/habDoble/vistaCamas.jpg", // URL de la imagen
                            contentDescription = "Imagen cargada desde URL",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .background(backgroundTransparet)
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(15.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(text = "Doble" , color = foreground, fontSize = fontTitleSize)
                            Text(text = "desde " + lista.find { tipo -> tipo.nombreTipo == "Doble" }?.precioBase.toString() + "€",
                                color = foreground, fontSize = fontSize)
                        }
                    }
                    //celda habitacion Individual
                    Box(modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = {
                            someBoxIsSelected = true
                            selectedBox = "Individual"
                        })
                        .fillMaxWidth()
                        .alpha(listaVisibles["Individual"]?.toFloat() ?: 0f)
                        .padding(paddingBox)) {
                        AsyncImage(
                            model = "$baseUrl/habIndividual/vistaMesita.jpg", // URL de la imagen
                            contentDescription = "Imagen cargada desde URL",
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .background(backgroundTransparet)
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(15.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(text = "Individual" , color = foreground, fontSize = fontTitleSize)
                            Text(text = "desde " + lista.find { tipo -> tipo.nombreTipo == "Individual" }?.precioBase.toString() + "€"
                                , color = foreground, fontSize=fontSize)
                        }
                    }
                }
            }
        }
    }
}


