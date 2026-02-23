package com.example.muscuapp_vmob_1.navigation

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Exercises : Screen("Exercises")
    object Entrainements : Screen("Entrainements")
    object Calendrier : Screen("Calendrier")
}