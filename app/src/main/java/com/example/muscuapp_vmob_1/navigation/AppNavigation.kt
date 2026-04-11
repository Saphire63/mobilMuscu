package com.example.muscuapp_vmob_1.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.muscuapp_vmob_1.ui.views.ListExercise
import com.example.muscuapp_vmob_1.ui.views.TrainingScreen
import com.example.muscuapp_vmob_1.ui.views.components.baseApp.AppTopBar
import com.example.muscuapp_vmob_1.ui.views.components.baseApp.AppBottomBar

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar() },
        bottomBar = { AppBottomBar(navController) }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                Text("Home")
            }

            composable(Screen.Exercises.route) {
                ListExercise(innerPaddingValues = innerPadding, navController)
            }

            composable(Screen.Training.route) {
                TrainingScreen(innerPadding= innerPadding)
            }

            composable(Screen.Calendar.route) {
                Text("Calendar")
            }
        }
    }
}
