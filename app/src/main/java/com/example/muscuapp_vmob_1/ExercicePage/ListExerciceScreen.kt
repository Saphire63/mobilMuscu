package com.example.muscuapp_vmob_1.ExercicePage

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
import com.example.muscuapp_vmob_1.model.deserializeMachinesFromJson


@Composable
fun ListExercice(innerPaddingValues: PaddingValues){
    val context = LocalContext.current
    val machines = remember { deserializeMachinesFromJson(context) }

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
