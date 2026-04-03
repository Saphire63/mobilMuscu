package com.example.muscuapp_vmob_1.ui.views.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muscuapp_vmob_1.domain.use_cases.CalculateChargeUseCase
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.ExerciseVM

@Composable
fun ExerciseCard(
    exercise: ExerciseVM,
    onDelete: () -> Unit,
    onEdit: (ExerciseVM) -> Unit,
    calculateChargeUseCase: CalculateChargeUseCase = CalculateChargeUseCase() 
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var percent by remember { mutableStateOf("") }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Supprimer") },
            text = { Text("Voulez-vous supprimer \"${exercise.name}\" ?") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    onDelete()

                }) {
                    Text("Oui", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Non")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2B2B2B), RoundedCornerShape(12.dp))
            .padding(16.dp)
            .clickable { expanded = !expanded }
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            // Image placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.DarkGray)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = exercise.name,
                    color = Color.White,
                    fontSize = 20.sp
                )

                Text(
                    text = "Max : ${exercise.max?.toString() ?: "No max yet"}",
                    color = Color.LightGray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = percent,
                        onValueChange = {
                            if (it.matches(Regex("^\\d*\\.?\\d*$"))) {
                                percent = it
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.40f)
                            .height(40.dp)
                            .background(Color.DarkGray, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        singleLine = true,
                        cursorBrush = SolidColor(Color.White),
                        textStyle = TextStyle(color = Color.White),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),


                        decorationBox = { innerTextField ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    innerTextField()
                                }
                                Text("%", color = Color.White)
                            }
                        }
                    )
                    Text(
                        modifier = Modifier.padding(start = 20.dp),
                        text="->"
                    )
                    Text(
                        modifier = Modifier.padding(start = 15.dp),
                        text = "${"%.1f".format(calculateChargeUseCase(percent, exercise.max))} kg"
                    )
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 4.dp),

                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Icon(
                    imageVector = if (expanded) {
                        Icons.Default.KeyboardArrowDown
                    } else {
                        Icons.AutoMirrored.Filled.KeyboardArrowRight
                    },
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = Color.White

                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    tint = Color.White,
                    contentDescription = "Edit",
                    modifier = Modifier.clickable{
                        onEdit(exercise)

                    }
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    tint = Color.White,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable{showDeleteDialog = true}

                )
            }

        }

        // Expanded description
        AnimatedVisibility(visible = expanded) {
            Text(
                text = exercise.description,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}
