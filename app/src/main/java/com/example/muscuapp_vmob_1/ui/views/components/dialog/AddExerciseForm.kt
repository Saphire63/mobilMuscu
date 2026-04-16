package com.example.muscuapp_vmob_1.ui.views.components.dialog

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
import com.example.muscuapp_vmob_1.domain.use_cases.exercise.AddEditExerciseEvent
import com.example.muscuapp_vmob_1.ui.viewmodel.AddEditExerciseViewModel

@Composable
fun AddExerciseDialog(
    viewModel: AddEditExerciseViewModel,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    val exerciseState = viewModel.exercise.value

    var showValidationError by remember { mutableStateOf(false) }

    // État local pour le texte du poids permettre une saisie fluide (ex: "", ".", "10.")
    var weightText by remember(exerciseState.id) {
        mutableStateOf(exerciseState.max?.let {
            if (it % 1.0f == 0.0f) it.toInt().toString() else it.toString()
        } ?: "")
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (exerciseState.id == 0) "Ajouter un exercice" else "Modifier l'exercice",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Nom de l'exercice
                OutlinedTextField(
                    value = exerciseState.name,
                    onValueChange = { viewModel.onEvent(AddEditExerciseEvent.EnteredName(it)) },
                    label = { Text("Nom") },
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Max de poids
                OutlinedTextField(
                    value = weightText,
                    onValueChange = { input ->
                        // On remplace la virgule par un point
                        val formattedText = input.replace(',', '.')
                        
                        // On autorise uniquement les chiffres et un seul point décimal
                        if (formattedText.isEmpty() || formattedText.matches(Regex("^\\d*\\.?\\d*$"))) {
                            weightText = formattedText
                            viewModel.onEvent(AddEditExerciseEvent.EnteredMax(formattedText.toFloatOrNull()))
                        }
                    },
                    label = { Text("Poids max (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // Description
                OutlinedTextField(
                    value = exerciseState.description,
                    onValueChange = { viewModel.onEvent(AddEditExerciseEvent.EnteredDescription(it)) },
                    label = { Text("Description") },
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Terminé")
                    Checkbox(
                        checked = exerciseState.isDone,
                        onCheckedChange = { 
                            viewModel.onEvent(AddEditExerciseEvent.ExerciseDone)
                            if (exerciseState.isDone) showValidationError = false
                        }
                    )
                }

                if (showValidationError && !exerciseState.isDone) { 
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
                if (exerciseState.isDone) { 
                    viewModel.onEvent(AddEditExerciseEvent.SaveExercise)
                    onSave()
                } else {
                    showValidationError = true 
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
