package com.example.muscuapp_vmob_1.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.muscuapp_vmob_1.domain.use_cases.training.AddEditTrainingEvent
import com.example.muscuapp_vmob_1.ui.viewmodel.AddEditTrainingViewModel
import com.example.muscuapp_vmob_1.ui.viewmodel.TrainingScreenViewModel
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingUiState
import com.example.muscuapp_vmob_1.ui.views.components.SearchBar
import com.example.muscuapp_vmob_1.ui.views.components.TrainingCard
import com.example.muscuapp_vmob_1.ui.views.components.forms.AddTrainingDialog

@Composable
fun TrainingScreen(innerPadding: PaddingValues){
    val trainingViewModel: TrainingScreenViewModel = hiltViewModel()
    val addEditTrainingViewModel: AddEditTrainingViewModel = hiltViewModel()
    val trainings by trainingViewModel.trainings.collectAsState()
    val searchQuery by trainingViewModel.searchQuery.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    if (showDialog){
        AddTrainingDialog(
            viewModel = addEditTrainingViewModel,
            onDismiss = {showDialog = false},
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
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            SearchBar(
                text = "Rechercher un entrainement",
                query = searchQuery,
                onQueryChange = { trainingViewModel.onSearchQueryChange(it) },
                modifier = Modifier.weight(1f)
            )

            Button(onClick = {
                addEditTrainingViewModel.onEvent(AddEditTrainingEvent.ResetForm)
                showDialog = true
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .padding(5.dp)

            )
            {
                Text("+")
            }
        }

        when (trainings) {
            is TrainingUiState.Loading -> {
                Text("Chargement...", modifier = Modifier.fillMaxWidth().padding(16.dp))
            }

            is TrainingUiState.Empty -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(if (searchQuery.isEmpty()) "Pas d’entraînements trouvés" else "Aucun résultat pour \"$searchQuery\"")
                    Button(
                        onClick = {
                            addEditTrainingViewModel.onEvent(AddEditTrainingEvent.ResetForm)
                            showDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Ajouter un entraînement")
                    }
                }
            }

            is TrainingUiState.Success -> {
                val trainingList = (trainings as TrainingUiState.Success).trainings
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(trainingList) { training ->
                        TrainingCard(
                            training = training,
                            onDelete = { trainingViewModel.deleteTraining(training) },
                            onEdit = { selectedTraining ->
                                addEditTrainingViewModel.onEvent(AddEditTrainingEvent.LoadTraining(selectedTraining))
                                showDialog = true
                            }
                        )
                    }
                }
            }

            is TrainingUiState.Error -> {
                Text(
                    text = (trainings as TrainingUiState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
            }
        }
    }
}