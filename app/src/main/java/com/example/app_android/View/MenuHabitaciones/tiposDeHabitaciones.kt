package com.example.app_android.View.MenuHabitaciones

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
@Preview
fun pantallaHabitaciones(){
    BoxWithConstraints {
        Column {
            //habitacion suite
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .background(Color.Yellow)
                    .fillMaxWidth()
                    .weight(0.4f)
                    .border(2.dp, Color.White)
                    .padding(15.dp)
                ,
                horizontalAlignment = Alignment.End
            ){
                Text( text = "Suite")
                Text(text = "4 adultos 2 menores")
            }
            Row (modifier = Modifier.weight(1f)){
                Column (modifier = Modifier.width(this@BoxWithConstraints.maxWidth*0.5f)){
                    //habitacion triple
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .background(Color.Magenta)
                            .border(2.dp, Color.White)
                            .fillMaxWidth()
                            .padding(15.dp)
                            .weight(0.5f)
                        ,
                        horizontalAlignment = Alignment.Start
                    ){
                        Text( text = "Triple")
                        Text(text = "3 adultos")
                    }
                    //habitacion familiar
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .background(Color.Green)
                            .weight(0.75f)
                            .fillMaxWidth()
                            .border(2.dp, Color.White)
                            .padding(15.dp)
                        ,
                        horizontalAlignment = Alignment.Start
                    ){
                        Text( text = "Familiar")
                        Text(text = "2 adultos 1 menor")
                    }
                }
                Column (modifier = Modifier.width(this@BoxWithConstraints.maxWidth*0.5f)){
                    //celda habitacion Doble
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .background(Color.Blue)
                            .weight(1.5f)
                            .fillMaxWidth()
                            .border(2.dp, Color.White)
                            .padding(15.dp)
                        ,
                        horizontalAlignment = Alignment.End
                    ){
                        Text( text = "Doble")
                        Text(text = "2 adultos")
                    }
                    //celda habitacion Individual
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .background(Color.Red)
                            .weight(1f)
                            .fillMaxWidth()
                            .border(2.dp, Color.White)
                            .padding(15.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(text = "Individual")
                        Text(text = "1 adulto")
                    }
                }
            }
        }
    }
}
