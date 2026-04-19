package com.example.muscuapp_vmob_1.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.muscuapp_vmob_1.domain.use_cases.training.AddEditTrainingEvent
import com.muscuapp_vmob_1.ui.viewmodel.training.AddEditTrainingViewModel
import com.muscuapp_vmob_1.ui.views.components.ExerciseListItem
import com.muscuapp_vmob_1.ui.views.components.dialog.ExercisePickerDialog
import com.muscuapp_vmob_1.domain.use_cases.training.AddEditTrainingEvent
import com.muscuapp_vmob_1.ui.viewmodel.AddEditTrainingViewModel
import com.muscuapp_vmob_1.ui.views.components.ExerciseListItem
import com.muscuapp_vmob_1.ui.views.components.dialog.ExercisePickerDialog

@Composable
fun AddEditTrainingScreen(
    navController: NavController,
    viewModel: AddEditTrainingViewModel
) {
    val trainingState = viewModel.training.value
    val availableExercises by viewModel.availableExercises.collectAsState()
    
    var showExercisePicker by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Column()


    {
        Box(
            Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            IconButton(
                onClick = {navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text( if (trainingState.id ==0) "Créer un entraînement" else "Modifier l'entraînement",
                )

            TextButton(  {
                viewModel.onEvent(AddEditTrainingEvent.SaveTraining)
                navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text("Enregistrer", color = Color.Red, fontWeight = FontWeight.Bold)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = trainingState.name,
                onValueChange = { viewModel.onEvent(AddEditTrainingEvent.EnteredName(it)) },
                label = { Text("Nom de l'entraînement") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = trainingState.type,
                onValueChange = { viewModel.onEvent(AddEditTrainingEvent.EnteredType(it)) },
                label = { Text("Type (ex: Pull, Push, Legs)") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = trainingState.description,
                onValueChange = { viewModel.onEvent(AddEditTrainingEvent.EnteredDescription(it)) },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Exercices (${trainingState.exercises.size})",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { showExercisePicker = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text("Ajouter")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (trainingState.exercises.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("Aucun exercice ajouté", color = Color.Gray)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    itemsIndexed(trainingState.exercises) { index, exercise ->
                        ExerciseListItem(
                            index = index,
                            exercise = exercise,
                            onRemove = { viewModel.onEvent(AddEditTrainingEvent.RemoveExerciseAt(index)) }
                        )
                    }
                }
            }
        }
    }

    if (showExercisePicker) {
        ExercisePickerDialog(
            availableExercises = availableExercises,
            onDismiss = { showExercisePicker = false },
            onSelect = { exercise ->
                viewModel.onEvent(AddEditTrainingEvent.AddExercise(exercise))
                showExercisePicker = false
            }
        )
    }
}
