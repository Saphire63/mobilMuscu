package com.example.muscuapp_vmob_1.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.TableInfo
import com.example.muscuapp_vmob_1.domain.use_cases.exercise.AddEditExerciseEvent
import com.example.muscuapp_vmob_1.ui.viewmodel.TrainingScreenViewModel
import com.example.muscuapp_vmob_1.ui.views.components.SearchBar
import com.google.android.material.internal.ViewUtils
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun TrainingScreen(innerPadding: PaddingValues){
    val trainingViewModel: TrainingScreenViewModel = hiltViewModel()
    val searchQuery by trainingViewModel.searchQuery.collectAsState()
    Column() {
        Row (
            modifier= Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { trainingViewModel.onSearchQueryChange(it) },
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
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier
                    .padding(5.dp)

            )
            {
                Text("+")
            }
        }
    }
}