package com.example.muscuapp_vmob_1.domain.use_cases.training

import com.example.muscuapp_vmob_1.data.repository.entrainements.TrainingRepository
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import javax.inject.Inject

class UpsertTrainingUseCase @Inject constructor(
    private val repository: TrainingRepository
) {
    suspend operator fun invoke (training: TrainingVM){
        repository.upsertTraining(training)
    }
}