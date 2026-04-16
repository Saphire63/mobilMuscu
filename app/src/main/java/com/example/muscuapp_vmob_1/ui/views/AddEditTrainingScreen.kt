package com.example.muscuapp_vmob_1.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.muscuapp_vmob_1.domain.use_cases.training.AddEditTrainingEvent
import com.example.muscuapp_vmob_1.ui.viewmodel.AddEditTrainingViewModel
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTrainingScreen(
    navController: NavController,
    viewModel: AddEditTrainingViewModel
) {
    val trainingState = viewModel.training.value
    val availableExercises by viewModel.availableExercises.collectAsState()
    
    var showExercisePicker by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(if (trainingState.id == 0) "Créer un entraînement" else "Modifier l'entraînement") 
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                actions = {
                    TextButton(onClick = {
                        viewModel.onEvent(AddEditTrainingEvent.SaveTraining)
                        navController.popBackStack()
                    }) {
                        Text("ENREGISTRER", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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

@Composable
fun ExerciseListItem(
    index: Int,
    exercise: ExerciseVM,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2B2B2B))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color.Red,
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.size(24.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = (index + 1).toString(),
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Text(
                text = exercise.name,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, contentDescription = "Supprimer", tint = Color.Gray)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisePickerDialog(
    availableExercises: List<ExerciseVM>,
    onDismiss: () -> Unit,
    onSelect: (ExerciseVM) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredExercises = availableExercises.filter { 
        it.name.contains(searchQuery, ignoreCase = true) 
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = { Text("Ajouter un exercice") },
        text = {
            Column {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Rechercher...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                LazyColumn(modifier = Modifier.height(400.dp)) {
                    itemsIndexed(filteredExercises) { _, exercise ->
                        ListItem(
                            headlineContent = { Text(exercise.name) },
                            modifier = Modifier.clickable { onSelect(exercise) }
                        )
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp), color = Color.Gray.copy(alpha = 0.2f))
                    }
                }
            }
        }
    )
}
