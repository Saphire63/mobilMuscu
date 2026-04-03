package com.example.muscuapp_vmob_1.domain.use_cases

import com.example.muscuapp_vmob_1.data.repository.exercices.ExerciseRepository
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.ExerciseVM
import javax.inject.Inject

class GetExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend operator fun invoke(id: Int): ExerciseVM? {
        return repository.getExercise(id)
    }
}