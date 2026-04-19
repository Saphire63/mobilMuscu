package com.muscuapp_vmob_1.domain.use_cases.exercise

import com.muscuapp_vmob_1.data.repository.exercices.ExerciseRepository
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExercisesUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(): Flow<List<ExerciseVM>> {
        return repository.getExercises()
    }
}