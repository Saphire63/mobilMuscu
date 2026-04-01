package com.example.muscuapp_vmob_1.ui.views

import android.R.attr.onClick
import android.widget.Button
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.muscuapp_vmob_1.ui.viewmodel.ListExerciceViewModel
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.MachineUiState
import com.example.muscuapp_vmob_1.ui.views.components.ExerciceCard
import com.example.muscuapp_vmob_1.ui.views.components.SearchBar


@Composable
fun ListExercice(innerPaddingValues: PaddingValues) {
    fun addMachine(){
        print ("not inplemented yet")
    }
    Column() {
        val viewModel: ListExerciceViewModel = hiltViewModel()
        val machines by viewModel.machines.collectAsState()
        Row (
            modifier= Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            SearchBar( modifier = Modifier.weight(1f))
            Button(onClick = {addMachine()},
                colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            ),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.padding(5.dp)
            )
            {
                Text("+")
            }
        }
        when (machines) {
            is MachineUiState.Loading -> {
                Text("Chargement...", modifier = Modifier.fillMaxWidth())
            }
            is MachineUiState.Empty -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Pas d’entraînement trouvé")
                    Button(onClick = {addMachine()},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
                    {
                        Text("Créer une machine")
                    }
                }
            }
            is MachineUiState.Success -> {
                val machines = (machines as MachineUiState.Success).machines
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(machines) { machine ->
                        ExerciceCard(machine)
                    }
                }
            }
            is MachineUiState.Error -> {
                Text("Erreur lors du chargement", color = Color.Red)
            }
        }
    }
}
