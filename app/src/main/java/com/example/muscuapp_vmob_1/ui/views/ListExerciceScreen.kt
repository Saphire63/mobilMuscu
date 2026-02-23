package com.example.muscuapp_vmob_1.ui.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.muscuapp_vmob_1.data.repository.Exercices.MachineFichierRepository
import com.example.muscuapp_vmob_1.ui.components.ExerciceCard
import com.example.muscuapp_vmob_1.ui.components.SearchBar


@Composable
fun ListExercice(innerPaddingValues: PaddingValues){
    SearchBar()
    val context = LocalContext.current
    val repository = MachineFichierRepository(context)
    val machines = remember { repository.getMachine()}

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
