package com.example.unitconverter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}


@Composable
fun shipGame(){
    val context = LocalContext.current
    val direction = remember{ mutableStateOf("North")}
    val treasure = remember{ mutableStateOf(0)}
    val health = remember { mutableStateOf(100)}

    Column {

        Text(text = "Treasure found: ${treasure.value}")
        Text(text = "Going in direction: ${direction.value}")
        Text(text = "Current health ${health.value}")

        Button(onClick = {direction.value = "North"
                            if(health.value > 0) {
                                if(Random.nextBoolean()){
                                treasure.value += 1
                                }
                            if(Random.nextBoolean()){
                                health.value -= 10
                            }
                            }
            else{
                Toast.makeText(context, "no health", Toast.LENGTH_LONG).show()
            }
        }) {
            Text(text = "Sail North")
        }

        Button(onClick = {direction.value = "South"
            if(Random.nextBoolean()){
                treasure.value += 1
            }
            if(Random.nextBoolean()){
                health.value -= 10
            }
        }) {
            Text(text = "Sail South")
        }

        Button(onClick = {direction.value = "East"
            if(Random.nextBoolean()){
                treasure.value += 1
            }
            if(Random.nextBoolean()){
                health.value -= 10
            }
        }) {
            Text(text = "Sail East")
        }

        Button(onClick = {direction.value = "West"
            if(Random.nextBoolean()){
                treasure.value += 1
            }
            if(Random.nextBoolean()){
                health.value -= 10
            }
        }) {
            Text(text = "West")
        }
    }
}


@Composable
fun UnitConverter(){

    var inputValue by remember { mutableStateOf("")}
    var outputValue by remember { mutableStateOf("")}
    var inputUnit  by remember { mutableStateOf("")}
    var outputUnit by remember { mutableStateOf("")}
    var iExpand by remember { mutableStateOf(false)}
    var oExpand by remember { mutableStateOf(false)}
    val conversionFactor = remember  { mutableStateOf(1.0)}
    val oConversionFactor = remember { mutableStateOf(1.0)}

    fun convertUnits(){
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * conversionFactor.value * 100 / oConversionFactor.value).roundToInt() / 100
        outputValue = result.toString()
    }

    Column (modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

    ){
       Text(text = "Unit Convert",
           style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = inputValue, onValueChange ={inputValue = it
                                                             convertUnits()},
            label ={ Text(text = "Enter the value")})
        Spacer(modifier = Modifier.height(16.dp))


        Row {
            Box{
                //Input Button
                Button(onClick = { iExpand  = true}) {
                    Text(text = inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription="Arrow Down")
                }
                DropdownMenu(expanded = iExpand, onDismissRequest = { iExpand = false }) {

                    DropdownMenuItem(text = { Text(text = "Centimeters") },
                        onClick = {iExpand = false
                        conversionFactor.value = 0.01
                        inputUnit = "Centimeters"
                        convertUnits()
                        })
                    DropdownMenuItem(text = { Text(text = "Meters")},
                        onClick = {iExpand = false
                            conversionFactor.value = 1.0
                            inputUnit = "Meters"
                            convertUnits()})
                    DropdownMenuItem(text = { Text(text = "Feet")},
                        onClick = {iExpand = false
                        conversionFactor.value = 0.3048
                        inputUnit = "Feets"
                        convertUnits()})

                }

            }
            Spacer(modifier = Modifier.width(16.dp))
            Box{
                //Output button
                Button(onClick = { oExpand = true }) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription="Arrow Down")
                }

                DropdownMenu(expanded = oExpand, onDismissRequest = { oExpand = false }) {
                    DropdownMenuItem(text = { Text(text = "Centimeters") },
                        onClick = { oExpand = false
                            oConversionFactor.value = 0.01
                            outputUnit = "Centimeters"
                            convertUnits()})
                    DropdownMenuItem(text = { Text(text = "Meters")},
                        onClick = { oExpand = false
                            oConversionFactor.value = 1.0
                            outputUnit = "Meters"
                            convertUnits()})
                    DropdownMenuItem(text = { Text(text = "Feet")},
                        onClick = { oExpand = false
                            oConversionFactor.value = 0.3048
                            outputUnit = "Feets"
                            convertUnits() })

                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Result: $outputValue $outputUnit",
            style = MaterialTheme.typography.headlineMedium)
    }



}


@Preview (showBackground = true)
@Composable
fun UnitConverterPreview(){
    UnitConverter()
}