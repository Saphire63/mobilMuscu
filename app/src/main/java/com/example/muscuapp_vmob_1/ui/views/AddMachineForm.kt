package com.example.muscuapp_vmob_1.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muscuapp_vmob_1.data.AddEditMachineEvent
import com.example.muscuapp_vmob_1.ui.viewmodel.AddEditMachineViewModel

@Composable
fun AddMachineDialog(
    viewModel: AddEditMachineViewModel,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    val machineState = viewModel.machine.value

    var showValidationError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (machineState.id == 0) "Ajouter une machine" else "Modifier la machine",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Nom de la machine
                OutlinedTextField(
                    value = machineState.name,
                    onValueChange = { viewModel.onEvent(AddEditMachineEvent.EnteredName(it)) },
                    label = { Text("Nom") },
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Max de poids
                OutlinedTextField(
                    value = machineState.max?.toString() ?: "",
                    onValueChange = { newText ->
                        if (newText.isEmpty()) {
                            viewModel.onEvent(AddEditMachineEvent.EnteredMax(null))
                        } else if (newText.matches(Regex("^\\d*\\.?\\d*$"))) {
                            viewModel.onEvent(AddEditMachineEvent.EnteredMax(newText.toFloatOrNull() ?: 0f))
                        }
                    },
                    label = { Text("Poids max (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Description
                OutlinedTextField(
                    value = machineState.description,
                    onValueChange = { viewModel.onEvent(AddEditMachineEvent.EnteredDescription(it)) },
                    label = { Text("Description") },
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Terminé")
                    Checkbox(
                        checked = machineState.isDone,
                        onCheckedChange = { 
                            viewModel.onEvent(AddEditMachineEvent.MachineDone)
                            if (machineState.isDone) showValidationError = false
                        }
                    )
                }

                if (showValidationError && !machineState.isDone) { // message d'erreur qui dépend de la variable local en mutableStateOf
                    Text(
                        text = "Veuillez cocher 'Terminé' pour enregistrer",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (machineState.isDone) { 
                    viewModel.onEvent(AddEditMachineEvent.SaveMachine)
                    onSave()
                } else {
                    showValidationError = true // si c'est pas terminé alors change a tru le message d'erreure qui s'affiche dans le truc
                }
            }) {
                Text("Enregistrer")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}
