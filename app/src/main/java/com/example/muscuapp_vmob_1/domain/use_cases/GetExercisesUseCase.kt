package com.example.muscuapp_vmob_1.domain.use_cases

import com.example.muscuapp_vmob_1.data.repository.exercices.ExerciseRepository
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.ExerciseVM
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExercisesUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    operator fun invoke(): Flow<List<ExerciseVM>> {
        return repository.getExercises()
    }
}