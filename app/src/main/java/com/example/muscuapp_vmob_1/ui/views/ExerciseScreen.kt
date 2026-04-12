package com.example.muscuapp_vmob_1.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.muscuapp_vmob_1.domain.use_cases.exercise.AddEditExerciseEvent
import com.example.muscuapp_vmob_1.ui.viewmodel.AddEditExerciseViewModel
import com.example.muscuapp_vmob_1.ui.viewmodel.ExerciseScreenViewModel
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseUiState
import com.example.muscuapp_vmob_1.ui.views.components.forms.AddExerciseDialog
import com.example.muscuapp_vmob_1.ui.views.components.ExerciseCard
import com.example.muscuapp_vmob_1.ui.views.components.SearchBar


@Composable
fun ListExercise(innerPaddingValues: PaddingValues, navController: NavController) {
    val listViewModel: ExerciseScreenViewModel = hiltViewModel()
    val editViewModel: AddEditExerciseViewModel = hiltViewModel()
    val exercises by listViewModel.exercises.collectAsState()
    val searchQuery by listViewModel.searchQuery.collectAsState()
    
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AddExerciseDialog(
            viewModel = editViewModel,
            onDismiss = { showDialog = false },
            onSave = {
                showDialog = false
            }
        )
    }

    Column() {
        Row (
            modifier= Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchBar(
                text = "Rechercher un exercice...",
                query = searchQuery,
                onQueryChange = { listViewModel.onSearchQueryChange(it) },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                editViewModel.onEvent(AddEditExerciseEvent.ResetForm)
                showDialog = true
            },
                colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ),
                modifier = Modifier
                    .padding(5.dp)
                    .size(43.dp),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
        when (exercises) {

            is ExerciseUiState.Loading -> {
                Text("Chargement...", modifier = Modifier.fillMaxWidth())
            }


            is ExerciseUiState.Empty -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(if (searchQuery.isEmpty()) "Pas d’exercices trouvé" else "Aucun résultat pour \"$searchQuery\"")
                    Button(
                        onClick = {
                            editViewModel.onEvent(AddEditExerciseEvent.ResetForm)
                            showDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    {
                        Text("Ajouter un exercice")
                    }
                }
            }


            is ExerciseUiState.Success -> {
                val exercisesList = (exercises as ExerciseUiState.Success).exercises
                LazyColumn(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)

                    ,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(exercisesList) { exercise ->
                        ExerciseCard(
                            exercise = exercise,
                            onDelete = { listViewModel.deleteExercise(exercise) },
                            onEdit = { selectedExercise ->
                                editViewModel.onEvent(
                                    AddEditExerciseEvent.LoadExercise(
                                        selectedExercise
                                    )
                                )
                                showDialog = true
                            },
                            onEditImage = { uri ->
                                editViewModel.onEvent(AddEditExerciseEvent.LoadExercise(exercise))
                                editViewModel.onEvent(AddEditExerciseEvent.UpdateImageUri(uri))
                            },
                            onCalculateCharge = {percent, max -> listViewModel.calculateCharge(percent, max)}
                        )
                    }
                }
            }

            is ExerciseUiState.Error -> {
                Text("Erreur lors du chargement", color = Color.Red)
            }
        }
    }
}
