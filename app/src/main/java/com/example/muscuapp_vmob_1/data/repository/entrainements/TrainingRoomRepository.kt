package com.example.muscuapp_vmob_1.data.repository.entrainements

import com.example.muscuapp_vmob_1.data.source.TrainingDao
import com.example.muscuapp_vmob_1.data.source.TrainingSegmentsDao
import com.example.muscuapp_vmob_1.data.source.relations.TrainingWithSegments
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import androidx.room.Transaction
import com.example.muscuapp_vmob_1.domain.model.TrainingSegmentsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingRoomRepository @Inject constructor(
    private  val trainingDao: TrainingDao,
    private val trainingSegmentsDao: TrainingSegmentsDao
): TrainingRepository {

    override fun getTrainings(): Flow<List<TrainingVM>> {
        return trainingDao.getTrainingsWithSegments().map { relations ->
            relations.map { relation ->
                TrainingVM.fromEntity(
                    relation.training,
                    relation.segments.map { it.exercises }
                )
            }
        }
    }

    override suspend fun getTrainingWithSegments(id: Int): TrainingWithSegments {
        return trainingDao.getTrainingWithSegments(id)
    }

    override suspend fun deleteTraining(training: TrainingVM) {
        val trainingEntity = training.toEntity()
        trainingDao.deleteTraining(trainingEntity)
    }

    @Transaction
    override suspend fun upsertTraining(training: TrainingVM) {
        val trainingEntity = training.toEntity()
        val trainingId = trainingDao.upsertTraining(trainingEntity).toInt()
        
        // Si c'est une mise à jour, on remplace tout
        trainingSegmentsDao.deleteByTrainingId(if (training.id == 0) trainingId else training.id)
        
        val segments = training.exercises.mapIndexed { index, exercise ->
            TrainingSegmentsEntity(
                id = 0,
                trainingId = if (training.id == 0) trainingId else training.id,
                exerciseId = exercise.id,
                order = index
            )
        }
        trainingSegmentsDao.upsertSegments(segments)
    }

}