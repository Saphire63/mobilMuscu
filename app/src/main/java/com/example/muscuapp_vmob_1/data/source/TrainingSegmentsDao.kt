package com.example.muscuapp_vmob_1.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.muscuapp_vmob_1.data.source.relations.TrainingWithSegments
import com.example.muscuapp_vmob_1.domain.model.TrainingSegmentsEntity

@Dao
interface TrainingSegmentsDao {
    @Upsert
    suspend fun upsertSegments(segments: List<TrainingSegmentsEntity>)

    @Delete
    suspend fun deleteSegment(segment: TrainingSegmentsEntity)
}