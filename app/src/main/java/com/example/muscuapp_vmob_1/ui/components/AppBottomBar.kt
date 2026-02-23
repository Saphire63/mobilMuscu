package com.example.muscuapp_vmob_1.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.muscuapp_vmob_1.navigation.Screen

@Composable
fun AppBottomBar(navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        Screen.Home,
        Screen.Exercises,
        Screen.Entrainements,
        Screen.Calendrier
    )
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.Build,
        Icons.Filled.FitnessCenter,
        Icons.Filled.CalendarMonth
    )

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = Color(0xFF1A1A1A)
    ) {
        items.forEachIndexed { index, screen ->

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = screen.route
                    )
                },
                label = { Text(screen.route) },

                selected = currentRoute == screen.route,

                onClick = {
                    navController.navigate(screen.route) {

                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Red,
                    unselectedIconColor = Color.White,
                    selectedTextColor = Color.Red,
                    unselectedTextColor = Color.White,
                    indicatorColor = Color(0xFF3D3D3D)
                )
            )
        }
    }
}