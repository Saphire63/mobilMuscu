package com.example.muscuapp_vmob_1.navigation

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Exercises : Screen("Exercises")
    object Training : Screen("Training")
    object Calendar : Screen("Calendar")
    object AddEditTraining : Screen("AddEditTraining?trainingId={trainingId}") {
        fun passId(id: Int? = null): String {
            return "AddEditTraining" + (id?.let { "?trainingId=$it" } ?: "")
        }
    }
}