package com.example.muscuapp_vmob_1.data

import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM

sealed interface AddEditExerciseEvent {
    data class EnteredId(val id: Int): AddEditExerciseEvent
    data class EnteredName(val name: String): AddEditExerciseEvent
    data class EnteredMax(val max: Float?): AddEditExerciseEvent
    data class EnteredDescription(val description: String): AddEditExerciseEvent
    data object ExerciseDone: AddEditExerciseEvent
    data object SaveExercise: AddEditExerciseEvent
    data class LoadExercise(val exercise: ExerciseVM): AddEditExerciseEvent
    data object ResetForm: AddEditExerciseEvent
}