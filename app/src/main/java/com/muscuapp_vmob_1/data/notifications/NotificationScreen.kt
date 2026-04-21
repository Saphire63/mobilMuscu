package com.muscuapp_vmob_1.data.notifications

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.core.content.ContextCompat
import scheduleNotification
import showMyNotification

@Composable
fun MonEcranPrincipal() {
    val context = LocalContext.current

    // État pour suivre si la permission est accordée
    var isPermissionGranted by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
        )
    }

    // Préparer le lanceur de permission
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        isPermissionGranted = isGranted
        if (!isGranted) {
            // L'utilisateur a refusé, vous pourriez afficher un message ici
        }
    }

    // Demander la permission automatiquement au lancement si nécessaire
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !isPermissionGranted) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    // Bouton pour tester la notification
    Button(onClick = {
        // Si on est sur Android 13 ou plus
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // On vérifie si la permission a bien été accordée
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                showMyNotification(context)
            } else {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            // Pas besoin de permission avant Android 13
            showMyNotification(context)
        }
    }) {
        Text("Envoyer une notification")
    }

    Button(onClick = {
        scheduleNotification(context)
        Toast.makeText(context, "Notification prévue pour 19h05", Toast.LENGTH_SHORT).show()
    }) {
        Text("Programmer à 19h05")
    }
}
