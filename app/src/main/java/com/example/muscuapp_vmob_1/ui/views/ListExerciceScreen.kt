package com.example.muscuapp_vmob_1.ui.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.muscuapp_vmob_1.ui.viewmodel.ListExerciceViewModel
import com.example.muscuapp_vmob_1.ui.views.components.ExerciceCard
import com.example.muscuapp_vmob_1.ui.views.components.SearchBar


@Composable
fun ListExercice(innerPaddingValues: PaddingValues){
    SearchBar()
    val viewModel: ListExerciceViewModel = hiltViewModel()
    val machines by viewModel.machines.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(innerPaddingValues)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(machines) { machine ->
            ExerciceCard(machine)
        }
    }
}
