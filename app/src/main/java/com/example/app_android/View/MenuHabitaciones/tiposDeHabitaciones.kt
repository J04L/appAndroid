package com.example.app_android.View.MenuHabitaciones

import android.content.Context
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.app_android.Model.TipoHabitacion
import com.example.app_android.ViewModel.HabitacionViewModel

@Composable
fun pantallaHabitaciones(viewModel: HabitacionViewModel){
    var lista = viewModel.tipoHabitaciones.collectAsState().value;
    BoxWithConstraints(){
        var isSelected by remember { mutableStateOf(false) }
        var selectecBox = "";
        val opacity = animateFloatAsState(
            targetValue = if(isSelected) 0f else 1f,
            animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
        )
        val foreground = Color.White
        val fontTitleSize = 33.sp
        val fontSize = 20.sp
        val borderColor = Color.White
        val backgroundTransparet = Color.Black.copy(alpha = 0.65F)
        val baseUrl = "http://192.168.1.33:3036/img/habitaciones"
        Column {
            //habitacion suite
            Box(modifier = Modifier
                .weight(0.4f)
                .clickable(onClick = {
                    selectecBox = "suite"
                    isSelected = !isSelected
                })
                .fillMaxWidth()
                .alpha(if (selectecBox == "suite") 1f else opacity.value)
                .border(2.dp, borderColor)){
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
                            selectecBox = "triple"
                            isSelected = !isSelected
                        })
                        .fillMaxWidth()
                        .alpha(if (selectecBox == "triple") 1f else opacity.value)
                        .border(2.dp, borderColor)){
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
                        .weight(1f)
                        .clickable(onClick = {
                            selectecBox = "familiar"
                            isSelected = !isSelected
                        })
                        .fillMaxWidth()
                        .alpha(if (selectecBox == "familiar") 1f else opacity.value)
                        .border(2.dp, Color.White)) {
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
                            selectecBox = "doble"
                            isSelected = !isSelected
                        })
                        .fillMaxWidth()
                        .alpha(if (selectecBox == "doble") 1f else opacity.value)
                        .border(2.dp, borderColor)) {
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
                            selectecBox = "individual"
                            isSelected = !isSelected
                        })
                        .fillMaxWidth()
                        .alpha(if (selectecBox == "individual") 1f else opacity.value)
                        .border(2.dp, borderColor)) {
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
@Composable
fun AnimatedFocusScreen() {
    var selectedBox by remember { mutableStateOf<Int?>(null) } // Indica el Box seleccionado
    val transition = updateTransition(targetState = selectedBox, label = "screenTransition")

    // Animamos la opacidad de los elementos no seleccionados
    val contentOpacity by transition.animateFloat(label = "contentOpacity") { boxId ->
        if (boxId == null) 1f else 0f // Desvanece todo excepto el Box seleccionado
    }

    // Animamos el color de fondo de la pantalla
    val backgroundColor by transition.animateColor(label = "backgroundColor") { boxId ->
        if (boxId == null) Color.LightGray else Color.White // Blanco cuando un Box está seleccionado
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(3) { rowIndex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(contentOpacity), // Desaparece los elementos si hay un Box seleccionado
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    repeat(2) { boxIndex ->
                        val boxId = rowIndex * 2 + boxIndex
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .background(
                                    if (selectedBox == boxId) Color.Blue else Color.Red,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    selectedBox = if (selectedBox == boxId) null else boxId
                                } // Alternar selección
                        )
                    }
                }
            }
        }
    }
}

