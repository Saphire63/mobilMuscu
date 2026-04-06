package com.example.muscuapp_vmob_1.data.source

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.muscuapp_vmob_1.domain.model.TrainingEntity
import com.example.muscuapp_vmob_1.domain.model.TrainingWithExercises
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import kotlinx.coroutines.flow.Flow

interface TrainingDao {


    @Query("SELECT * FROM training")
    fun getTraining(): Flow<List<TrainingVM>>

    @Transaction
    @Query("SELECT * FROM training WHERE id =:id")
    suspend fun getTrainingWithExercise(id: Int): TrainingWithExercises

    @Delete
    suspend fun deleteTraining(trainingVM: TrainingEntity)

    @Upsert
    suspend fun upsertTraining(training: TrainingEntity)

}