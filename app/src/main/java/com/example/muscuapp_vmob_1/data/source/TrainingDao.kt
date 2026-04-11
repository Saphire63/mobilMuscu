package com.example.muscuapp_vmob_1.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.muscuapp_vmob_1.data.source.relations.TrainingWithSegments
import com.example.muscuapp_vmob_1.domain.model.TrainingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {


    @Query("SELECT * FROM training")
    fun getTrainings(): Flow<List<TrainingEntity>>

    @Transaction
    @Query("SELECT * FROM training")
    fun getTrainingsWithSegments(): Flow<List<TrainingWithSegments>>

    @Transaction
    @Query("SELECT * FROM training WHERE id =:id")
    suspend fun getTrainingWithSegments(id: Int): TrainingWithSegments

    @Delete
    suspend fun deleteTraining(training: TrainingEntity)

    @Upsert
    suspend fun upsertTraining(training: TrainingEntity): Long


}