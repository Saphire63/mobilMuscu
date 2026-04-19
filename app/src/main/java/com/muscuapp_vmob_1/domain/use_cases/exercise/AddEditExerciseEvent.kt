package com.muscuapp_vmob_1.domain.use_cases.exercise

import android.net.Uri
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM

sealed interface AddEditExerciseEvent {
    data class EnteredName(val name: String): AddEditExerciseEvent
    data class EnteredMax(val max: Float?): AddEditExerciseEvent
    data class EnteredDescription(val description: String): AddEditExerciseEvent
    data class EnteredImageUri(val uri: String?): AddEditExerciseEvent
    data class UpdateImageUri(val uri: Uri?): AddEditExerciseEvent
    data object ExerciseDone: AddEditExerciseEvent
    data object SaveExercise: AddEditExerciseEvent
    data class LoadExercise(val exercise: ExerciseVM): AddEditExerciseEvent
    data object ResetForm: AddEditExerciseEvent
}