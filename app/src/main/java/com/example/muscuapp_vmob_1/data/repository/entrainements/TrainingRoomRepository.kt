package com.example.muscuapp_vmob_1.data.repository.entrainements

import com.example.muscuapp_vmob_1.data.source.TrainingDao
import com.example.muscuapp_vmob_1.data.source.TrainingSegmentsDao
import com.example.muscuapp_vmob_1.data.source.relations.TrainingWithSegments
import com.example.muscuapp_vmob_1.domain.model.TrainingEntity.Companion.toVM
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM.Companion.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingRoomRepository @Inject constructor(
    private  val trainingDao: TrainingDao,
    private val trainingSegmentsDao: TrainingSegmentsDao
): TrainingRepository {



    override fun getTrainings(): Flow<List<TrainingVM>> {
        return trainingDao.getTrainings().map { entities ->
            entities.map { it.toVM() }
        }
    }



    override suspend fun getTrainingWithSegments(id: Int): TrainingWithSegments {
        return trainingDao.getTrainingWithSegments(id)
    }

    override suspend fun deleteTraining(training: TrainingVM) {
        trainingDao.deleteTraining(training.toEntity())
    }

    override suspend fun upsertTraining(training: TrainingVM) {
        trainingDao.upsertTraining(training.toEntity())
    }

}