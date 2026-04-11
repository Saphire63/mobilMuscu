package com.example.muscuapp_vmob_1.domain.use_cases.training

import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM

sealed interface AddEditTrainingEvent {
    data class EnteredName(val name: String): AddEditTrainingEvent
    data class EnteredMax(val max: Float?): AddEditTrainingEvent
    data class EnteredDescription(val description: String): AddEditTrainingEvent
    data object ExerciseDone: AddEditTrainingEvent
    data object SaveExercise: AddEditTrainingEvent
    data class LoadExercise(val exercise: ExerciseVM): AddEditTrainingEvent
    data object ResetForm: AddEditTrainingEvent
}