package com.example.muscuapp_vmob_1.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.muscuapp_vmob_1.domain.model.TrainingSegmentsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingSegmentsDao {
    @Upsert
    suspend fun upsertSegment(segment: TrainingSegmentsEntity)

    @Upsert
    suspend fun upsertSegments(segments: List<TrainingSegmentsEntity>)

    @Query("SELECT MAX('order') FROM training_segment WHERE trainingId = :trainingId")
    suspend fun getMaxOrder(trainingId: Int): Int?

    @Query("SELECT * FROM training_segment WHERE trainingId = :trainingId ORDER BY 'order' ASC")
    fun getSegmentsForTraining(trainingId: Int): Flow<List<TrainingSegmentsEntity>>

    @Delete
    suspend fun deleteSegment(segment: TrainingSegmentsEntity)

    @Query("DELETE FROM training_segment WHERE trainingId = :trainingId")
    suspend fun deleteByTrainingId(trainingId: Int)
}
