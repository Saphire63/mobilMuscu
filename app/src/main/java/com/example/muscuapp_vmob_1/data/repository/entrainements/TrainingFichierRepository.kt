package com.example.muscuapp_vmob_1.data.repository.entrainements

import com.example.muscuapp_vmob_1.domain.model.Entrainements
import com.example.muscuapp_vmob_1.domain.model.TrainingWithExercises
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import kotlinx.coroutines.flow.Flow

class TrainingFichierRepository : TrainingRepository {
    override fun getTraining(): Flow<List<TrainingVM>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrainingWithExercise(id: Int): TrainingWithExercises {
        TODO("Not yet implemented")
    }

    override suspend fun upsertTraining(training: TrainingVM) {
        TODO("Not yet implemented")
    }


}