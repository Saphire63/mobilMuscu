package com.example.muscuapp_vmob_1.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.muscuapp_vmob_1.model.Machine

@Composable
fun ExerciceCard(machine: Machine) {
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
            // Image placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.DarkGray)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = machine.name,
                    color = Color.White,
                    fontSize = 20.sp
                )

                Text(
                    text = "Max : ${machine.max}",
                    color = Color.LightGray
                )

                Text(
                    text = "${machine.percentage}%    ${machine.weight} kg",
                    color = Color.LightGray
                )
            }

            Icon(
                imageVector = if (expanded) {
                    Icons.Default.KeyboardArrowDown
                } else {
                    Icons.AutoMirrored.Filled.KeyboardArrowRight
                },
                contentDescription = if (expanded) "Collapse" else "Expand",
                tint = Color.White
            )
        }

        // Expanded description
        AnimatedVisibility(visible = expanded) {
            Text(
                text = machine.description,
                color = Color.LightGray,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}
