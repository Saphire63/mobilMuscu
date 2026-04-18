package com.example.muscuapp_vmob_1.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.muscuapp_vmob_1.ui.viewmodel.AddEditTrainingViewModel
import com.example.muscuapp_vmob_1.ui.views.AddEditTrainingScreen
import com.example.muscuapp_vmob_1.ui.views.ListExercise
import com.example.muscuapp_vmob_1.ui.views.TrainingScreen
import com.example.muscuapp_vmob_1.ui.views.components.baseApp.AppTopBar
import com.example.muscuapp_vmob_1.ui.views.components.baseApp.AppBottomBar
import com.example.muscuapp_vmob_1.ui.views.HomePageScreen


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
                HomePageScreen(innerPaddingValues = innerPadding, navController)
            }

            composable(Screen.Exercises.route) {
                ListExercise(innerPaddingValues = innerPadding, navController)
            }

            composable(Screen.Training.route) {
                TrainingScreen(innerPadding= innerPadding, navController = navController)
            }

            composable(
                route = Screen.AddEditTraining.route,
                arguments = listOf(
                    navArgument("trainingId") {
                        type = NavType.IntType
                        defaultValue = -1
                    }
                )
            ) {
                val viewModel: AddEditTrainingViewModel = hiltViewModel()
                AddEditTrainingScreen(navController = navController, viewModel = viewModel)
            }

            composable(Screen.Calendar.route) {
                Text("Calendar")
            }
        }
    }
}
