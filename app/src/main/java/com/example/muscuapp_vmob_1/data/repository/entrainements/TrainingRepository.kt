package com.example.muscuapp_vmob_1.data.repository.entrainements

import com.example.muscuapp_vmob_1.domain.model.TrainingWithExercises
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {
    fun getTraining(): Flow<List<TrainingVM>>

    suspend fun getTrainingWithExercise(id: Int): TrainingWithExercises // l'entity avec des exercises dedans

    suspend fun upsertTraining(training: TrainingVM)

}