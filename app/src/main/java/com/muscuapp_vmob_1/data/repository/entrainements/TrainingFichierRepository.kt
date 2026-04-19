package com.muscuapp_vmob_1.data.repository.entrainements

import com.muscuapp_vmob_1.data.source.relations.TrainingWithSegments
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import kotlinx.coroutines.flow.Flow

class TrainingFichierRepository : TrainingRepository {
    override fun getTrainings(): Flow<List<TrainingVM>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainingWithSegments(id: Int): TrainingWithSegments {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTraining(training: TrainingVM) {
        TODO("Not yet implemented")
    }

    override suspend fun upsertTraining(training: TrainingVM) {
        TODO("Not yet implemented")
    }


}