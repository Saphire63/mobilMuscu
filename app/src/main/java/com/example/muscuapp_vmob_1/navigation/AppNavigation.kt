package com.example.muscuapp_vmob_1.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.muscuapp_vmob_1.ui.views.ListExercice
import com.example.muscuapp_vmob_1.ui.components.AppTopBar
import com.example.muscuapp_vmob_1.ui.components.AppBottomBar
@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF0D0D0D),
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
                ListExercice(innerPaddingValues = innerPadding)
            }

            composable(Screen.Entrainements.route) {
                Text("Workout")
            }

            composable(Screen.Calendrier.route) {
                Text("Calendar")
            }
        }
    }

}



