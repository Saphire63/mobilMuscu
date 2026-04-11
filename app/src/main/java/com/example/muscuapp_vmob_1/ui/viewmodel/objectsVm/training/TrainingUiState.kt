package com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training

sealed class TrainingUiState {
    object Loading : TrainingUiState()
    object Empty : TrainingUiState()
    data class Success(val trainings: List<TrainingVM> ): TrainingUiState()
    data class Error(val message: String ): TrainingUiState()
}