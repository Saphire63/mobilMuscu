package com.muscuapp_vmob_1.domain.use_cases.exercise

import com.muscuapp_vmob_1.data.repository.exercices.ExerciseRepository
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import javax.inject.Inject

class UpsertExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(exercise: ExerciseVM) {
        repository.upsertExercise(exercise)
    }
}