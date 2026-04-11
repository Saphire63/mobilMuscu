package com.example.muscuapp_vmob_1.ui.views.components.forms

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.muscuapp_vmob_1.domain.use_cases.training.AddEditTrainingEvent
import com.example.muscuapp_vmob_1.ui.viewmodel.AddEditTrainingViewModel

@Composable
fun AddTrainingDialog(
    viewModel: AddEditTrainingViewModel,
    onDismiss: () -> Unit,
    onSave: () -> Unit
){
    val trainingState = viewModel.training.value
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (trainingState.id==0) "Créer un entraînement " else "Modifier l'entrînement",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {

        },
        confirmButton = {
            TextButton(onClick = {
                viewModel.onEvent(event = AddEditTrainingEvent.SaveTraining)
                onSave() //Todo
            }) { }
        }
    )
}