package com.example.muscuapp_vmob_1.ui.views.components.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muscuapp_vmob_1.domain.use_cases.training.AddEditTrainingEvent
import com.example.muscuapp_vmob_1.ui.viewmodel.AddEditTrainingViewModel

@Composable
fun AddTrainingDialog(
    viewModel: AddEditTrainingViewModel,
    onDismiss: () -> Unit,
    onSave: () -> Unit
){
    val trainingState = viewModel.training.value
    val availableExercises by viewModel.availableExercises.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (trainingState.id == 0) "Créer un entraînement" else "Modifier l'entraînement",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Nom de l'entraînement
                OutlinedTextField(
                    value = trainingState.name,
                    onValueChange = { viewModel.onEvent(AddEditTrainingEvent.EnteredName(it)) },
                    label = { Text("Nom") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )

                OutlinedTextField(
                    value = trainingState.type,
                    onValueChange = { viewModel.onEvent(AddEditTrainingEvent.EnteredType(it)) },
                    label = { Text("Type (ex: Pull, Push, Legs)") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )

                OutlinedTextField(
                    value = trainingState.description,
                    onValueChange = { viewModel.onEvent(AddEditTrainingEvent.EnteredDescription(it)) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))


                Text(text = "Exercices", fontWeight = FontWeight.Bold)
                
                LazyColumn(
                    modifier = Modifier.height(200.dp).fillMaxWidth()
                ) {
                    items(availableExercises) { exercise ->
                        val isSelected = trainingState.exercises.any { it.id == exercise.id }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = { viewModel.onEvent(AddEditTrainingEvent.ToggleExerciseSelection(exercise)) }
                            )
                            Text(text = exercise.name)
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                viewModel.onEvent(event = AddEditTrainingEvent.SaveTraining)
                onSave()
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