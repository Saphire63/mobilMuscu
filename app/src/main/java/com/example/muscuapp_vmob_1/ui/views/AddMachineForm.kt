package com.example.muscuapp_vmob_1.ui.views
import android.R.attr.value
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.muscuapp_vmob_1.navigation.Screen
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.max

@Composable
fun DrawForm (navController : NavController){
    var nomMachine by remember { mutableStateOf("") }
    var idMachine by remember { mutableStateOf("") }
    var maxMachine by remember { mutableStateOf("") }
    var descriptionMachine by remember { mutableStateOf("") }

    Scaffold (
        floatingActionButton = {
            FloatingActionButton (onClick = {
                navController.navigate(
                    Screen.Exercises.route) }) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Save Story")
            }
        }
    )
    { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)
            )
        {
            Text("Ajouter ou modifier une machine",
                color = Color.Red,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center)

            //L'ID de la machine
            OutlinedTextField(
                value = idMachine,
                onValueChange = { newText ->
                    // Filter: Only allow digits (0-9)
                    if (newText.all { it.isDigit() }) {
                        idMachine = newText
                    }
                },
                label = { Text ("Id de la machine") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(20.dp),
            )


            //Le nom de la machine
            OutlinedTextField(
                value = nomMachine,
                onValueChange = { nouvelleValeur ->
                    nomMachine = nouvelleValeur
                },
                label = { Text("Nom de la machine") },
                modifier = Modifier.padding(20.dp),
            )

            //Le max de poids à cette machine
            OutlinedTextField(
                value = maxMachine,
                onValueChange = { newText ->
                    // Filter: Only allow digits (0-9)
                    if (newText.all { it.isDigit() }) {
                        maxMachine = newText
                    }
                },
                label = { Text ("Poids (kg) max à cette machine") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(20.dp),
            )

            //La description de la machine
            OutlinedTextField(
                value = descriptionMachine,
                onValueChange = { nouvelleValeur ->
                    descriptionMachine = nouvelleValeur
                },
                label = { Text("Description de la machine") },
                modifier = Modifier.padding(20.dp),
            )
        }

    }

}

