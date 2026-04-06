package com.example.muscuapp_vmob_1.data.repository.entrainements

import com.example.muscuapp_vmob_1.data.source.relations.TrainingWithSegments
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import kotlinx.coroutines.flow.Flow

interface TrainingRepository {
    fun getTrainings(): Flow<List<TrainingVM>>

    suspend fun getTrainingWithSegments(id: Int): TrainingWithSegments

    suspend fun deleteTraining(training: TrainingVM)

    suspend fun upsertTraining(training: TrainingVM)

}