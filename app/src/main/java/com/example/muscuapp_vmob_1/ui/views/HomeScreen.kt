package com.example.muscuapp_vmob_1.ui.views

import android.graphics.drawable.Icon
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.muscuapp_vmob_1.domain.use_cases.exercise.AddEditExerciseEvent
import com.example.muscuapp_vmob_1.ui.viewmodel.AddEditExerciseViewModel
import com.example.muscuapp_vmob_1.ui.viewmodel.ExerciseScreenViewModel
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseUiState
import com.example.muscuapp_vmob_1.ui.views.components.dialog.AddExerciseDialog
import com.example.muscuapp_vmob_1.ui.views.components.ExerciseCard
import com.example.muscuapp_vmob_1.ui.views.components.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomePageScreen(innerPaddingValues: PaddingValues, navController: NavController)
{

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    )
        {

            Text("Bienvenue, prêt pour ton nouvel entraînement quotidien ?",
                modifier = Modifier.padding(8.dp),
                color = Color.Red,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light
            )

            SectionContainer(
                title = "Entrainements épinglés",
                // Remplace par ton icône de punaise si tu l'as en drawable
                icon = Icons.Default.Star
            ) {
                // Ici tu appelleras tes TrainingCard
                // Exemple :
                // TrainingCard(title = "Entrainement1", type = "Power Lifting")
                // Spacer(modifier = Modifier.height(12.dp))
                // TrainingCard(title = "Entrainement2", type = "Cardio")
            }

            SectionContainer(
                title = "Entrainement du jour",
                icon = Icons.Default.DateRange // Icône calendrier
            ) {
                // La liste de tes machines ici
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
            containerColor = Color(0xFF252525) // Le gris foncé du fond de la carte
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Le Header de la carte (Titre + Icône)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = Color(0xFFE0E0E0), // Gris très clair / Blanc cassé
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFFAAAAAA), // Gris moyen pour l'icône
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Le contenu (tes TrainingCards) injecté ici
            content()
        }
    }
}