package com.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises

sealed class ExerciseUiState {
    object Loading : ExerciseUiState()
    object Empty : ExerciseUiState()
    data class Success(val exercises: List<ExerciseVM>) : ExerciseUiState()
    data class Error(val message: String) : ExerciseUiState()
}