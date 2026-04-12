package com.example.muscuapp_vmob_1.ui.views.components

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.BrokenImage
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.muscuapp_vmob_1.domain.use_cases.CalculateChargeUseCase
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import java.io.File
import java.io.FileOutputStream

@Composable
fun ExerciseCard(
    exercise: ExerciseVM,
    onDelete: () -> Unit,
    onEdit: (ExerciseVM) -> Unit,
    onEditImage: (Uri?) -> Unit,
    onCalculateCharge: (String, Float?) -> Float
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showImageOptions by remember { mutableStateOf(false) }
    var percent by remember { mutableStateOf("") }
    var tempUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    fun getTmpFileUri(): Uri {
        val tmpFile = File(context.cacheDir, "tmp_image_${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            tmpFile
        )
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        onEditImage(uri)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            onEditImage(tempUri)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val uri = getTmpFileUri()
            tempUri = uri
            cameraLauncher.launch(uri)
        }
    }

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
            Box {
                SubcomposeAsyncImage( //pour utiliser loading/ error dans le cas ou pas d'images
                    model = exercise.imageUri, // le chemin
                    contentDescription = "Image de ${exercise.name}",
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.DarkGray, RoundedCornerShape(8.dp))
                        .clickable {
                            showImageOptions = true
                        },
                    contentScale = ContentScale.Crop,
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Image,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    },
                    error = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.BrokenImage,
                                contentDescription = null,
                                tint = Color.Red.copy(alpha = 0.5f),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                )

                DropdownMenu(
                    expanded = showImageOptions,
                    onDismissRequest = { showImageOptions = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Galerie") },
                        onClick = {
                            showImageOptions = false
                            galleryLauncher.launch("image/*")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Appareil photo") },
                        onClick = {
                            showImageOptions = false
                            val permissionCheckResult = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                            if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                val uri = getTmpFileUri()
                                tempUri = uri
                                cameraLauncher.launch(uri)
                            } else {
                                permissionLauncher.launch(android.Manifest.permission.CAMERA)
                            }
                        }
                    )
                    if (!exercise.imageUri.isNullOrEmpty()) {
                        DropdownMenuItem(
                            text = { Text("Supprimer l'image", color = Color.Red) },
                            onClick = {
                                showImageOptions = false
                                onEditImage(null)
                            }
                        )
                    }
                }
            }

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
                            .height(30.dp)
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
                        text = "${"%.1f".format(onCalculateCharge(percent, exercise.max))} kg"
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
