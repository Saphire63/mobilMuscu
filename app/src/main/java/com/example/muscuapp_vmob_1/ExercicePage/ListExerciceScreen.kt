package com.example.muscuapp_vmob_1.ExercicePage

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.dp
import com.example.muscuapp_vmob_1.components.SearchBar
import com.example.muscuapp_vmob_1.model.machines


@Composable
fun ListExercice(innerPaddingValues: PaddingValues){
    SearchBar()
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
