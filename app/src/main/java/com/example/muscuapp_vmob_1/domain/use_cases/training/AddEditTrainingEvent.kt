package com.example.muscuapp_vmob_1.domain.use_cases.training

import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM

sealed interface AddEditTrainingEvent {
    data class EnteredName(val name: String): AddEditTrainingEvent
    data class EnteredDescription(val description: String): AddEditTrainingEvent
    data class EnteredType(val type: String): AddEditTrainingEvent
    data object SaveTraining: AddEditTrainingEvent
    data class LoadTraining(val exercise: ExerciseVM): AddEditTrainingEvent
    data object ResetForm: AddEditTrainingEvent
}