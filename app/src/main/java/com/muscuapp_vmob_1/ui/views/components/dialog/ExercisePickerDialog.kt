package com.muscuapp_vmob_1.ui.views.components.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM

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
