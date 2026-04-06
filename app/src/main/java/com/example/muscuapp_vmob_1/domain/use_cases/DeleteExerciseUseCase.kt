package com.example.muscuapp_vmob_1.domain.use_cases

import com.example.muscuapp_vmob_1.data.repository.exercices.ExerciseRepository
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import javax.inject.Inject

class DeleteExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(exercise: ExerciseVM) {
        repository.deleteExercise(exercise)
    }
}