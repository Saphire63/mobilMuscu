package com.example.muscuapp_vmob_1.ui.views
import android.R.attr.value
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.max
import com.example.muscuapp_vmob_1.data.source.AddEditMachineEvent
import com.example.muscuapp_vmob_1.ui.viewmodel.AddEditMachineViewModel


@Composable
fun DrawForm (navController : NavController, viewModel: AddEditMachineViewModel){
    val machineState = viewModel.machine.value

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
                value = machineState.id.toString(),
                onValueChange = { newText ->
                    // Filter: Only allow digits (0-9)
                    if (newText.all { it.isDigit() }) {
                        val newIdAsInt = newText.toIntOrNull() ?: 0
                        viewModel.onEvent(AddEditMachineEvent.EnteredId(newIdAsInt))
                    }
                },
                label = { Text ("Id de la machine") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(20.dp),
            )


            //Le nom de la machine
            OutlinedTextField(
                value = machineState.name,
                onValueChange = { newText ->
                    viewModel.onEvent(AddEditMachineEvent.EnteredName(newText))
                },
                label = { Text("Nom de la machine") },
                modifier = Modifier.padding(20.dp),
            )

            //Le max de poids à cette machine
            OutlinedTextField(
                value = machineState.max.toString(),
                onValueChange = { newText ->
                    // Filter: Only allow digits (0-9)
                    if (newText.all { it.isDigit() }) {
                        // Ici j'ai utilisé l'IA (Gemini) pour corriger l'erreur qui faisait que l'app plantait
                        val newMaxAsInt = newText.toIntOrNull() ?: 0
                        viewModel.onEvent(AddEditMachineEvent.EnteredMax(newMaxAsInt))
                    }
                },
                label = { Text ("Poids (kg) max à cette machine") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(20.dp),
            )

            //La description de la machine
            OutlinedTextField(
                value = machineState.description,
                onValueChange = { newText ->
                    viewModel.onEvent(AddEditMachineEvent.EnteredDescription(newText))

                },
                label = { Text("Description de la machine") },
                modifier = Modifier.padding(20.dp),
            )

            Row {
                Text(
                    text = "Done",
                    modifier = Modifier.align(Alignment.CenterVertically))
                Checkbox(
                    checked = machineState.isDone,
                    onCheckedChange = {
                        viewModel.onEvent(AddEditMachineEvent.MachineDone)
                    }
                )
            }
        }
    }
}

