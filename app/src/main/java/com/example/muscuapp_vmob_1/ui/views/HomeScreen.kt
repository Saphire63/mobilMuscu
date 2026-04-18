package com.example.muscuapp_vmob_1.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.muscuapp_vmob_1.navigation.Screen
import com.example.muscuapp_vmob_1.ui.viewmodel.TrainingScreenViewModel
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingUiState
import com.example.muscuapp_vmob_1.ui.views.components.TrainingCard
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM

@Composable
fun HomePageScreen(innerPaddingValues: PaddingValues, navController: NavController) {
    val trainingViewModel: TrainingScreenViewModel = hiltViewModel()
    val trainings by trainingViewModel.trainings.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(innerPaddingValues), // Ajout des inners paddings du Scaffold
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Bienvenue, prêt pour ton nouvel entraînement quotidien ?",
            modifier = Modifier.padding(8.dp, bottom = 20.dp),
            color = Color.Red,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Light
        )

        SectionContainer(
            title = "Entrainements épinglés",
            icon = Icons.Default.Star
        ) {
            // On vérifie l'état de la requête BDD comme dans TrainingScreen
            when (val state = trainings) {
                is TrainingUiState.Loading -> {
                    Text("Chargement...", color = Color(0xFFE0E0E0))
                }

                is TrainingUiState.Empty -> {
                    Text("Aucun entraînement épinglé", color = Color.Gray, fontSize = 14.sp)
                }

                is TrainingUiState.Success -> {
                    // On boucle sur la liste des entraînements
                    state.trainings.forEach { training ->
                        TrainingCard(
                            training = training,
                            onDelete = { trainingViewModel.deleteTraining(training) },
                            onEdit = { selectedTraining ->
                                navController.navigate(Screen.AddEditTraining.passId(selectedTraining.id))
                            }
                        )
                        // Espace entre chaque carte
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                is TrainingUiState.Error -> {
                    Text(
                        text = state.message,
                        color = Color.Red
                    )
                }
            }
        }

        SectionContainer(
            title = "Entrainement du jour",
            icon = Icons.Default.DateRange
        ) {
            when(val state = trainings){
                is TrainingUiState.Empty -> {
                    Text("Aucun entraînement créé", color = Color.Gray, fontSize = 14.sp)
                }

                is TrainingUiState.Success -> {
                    val toDisplay = state.trainings.random()
                    HomeTrainingCard(training = toDisplay )
                }
                else ->{
                    Text("Bz tmr...", color = Color(0xFFE0E0E0))
                }
            }
            // La liste de tes machines ici plus tard
        }
    }
}

@Composable
fun SectionContainer(
    title: String,
    icon: ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF252525)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = Color(0xFFE0E0E0),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFFAAAAAA),
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Le contenu injecté
            content()
        }
    }
}

@Composable
fun HomeTrainingCard(
    training: TrainingVM
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2B2B2B), RoundedCornerShape(12.dp))
            .padding(16.dp)
            .clickable { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Le rond rouge avec l'initiale
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color.Red, RoundedCornerShape(25.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (training.type.isNotEmpty()) training.type.take(1).uppercase() else "T",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Les informations textuelles
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = training.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = training.type,
                    color = Color.LightGray,
                    fontSize = 14.sp
                )
                if (training.exercises.isNotEmpty()) {
                    Text(
                        text = "${training.exercises.size} exercices",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Juste la flèche pour le côté dépliant, sans les boutons d'édition
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = if (expanded) "Réduire" else "Étendre",
                tint = Color.White
            )
        }

        // Le contenu dépliable reste le même
        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(top = 12.dp)) {
                if (training.description.isNotEmpty()) {
                    Text(
                        text = training.description,
                        color = Color.LightGray,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                if (training.exercises.isNotEmpty()) {
                    HorizontalDivider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))
                    training.exercises.forEach { exercise ->
                        Text(
                            text = "• ${exercise.name}",
                            color = Color.White,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
