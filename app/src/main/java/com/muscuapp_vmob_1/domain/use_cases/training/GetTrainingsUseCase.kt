package com.muscuapp_vmob_1.domain.use_cases.training

import com.muscuapp_vmob_1.data.repository.entrainements.TrainingRepository
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrainingsUseCase @Inject constructor(
    private val repository: TrainingRepository
) {
    operator fun invoke(): Flow<List<TrainingVM>> {
        return repository.getTrainings()
    }
}