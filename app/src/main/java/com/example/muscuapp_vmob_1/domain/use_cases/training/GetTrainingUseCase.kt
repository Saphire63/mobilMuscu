package com.example.muscuapp_vmob_1.domain.use_cases.training

import com.example.muscuapp_vmob_1.data.repository.entrainements.TrainingRepository
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import javax.inject.Inject

class GetTrainingUseCase @Inject constructor(
    private val repository: TrainingRepository
) {
    suspend operator fun invoke(id: Int): TrainingVM? {
        val trainingWithSegments = repository.getTrainingWithSegments(id)
        
        return TrainingVM.fromEntity(
            trainingWithSegments.training,
            trainingWithSegments.segments.map { it.exercises }
        )
    }
}