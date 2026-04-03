package com.example.muscuapp_vmob_1.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.muscuapp_vmob_1.domain.model.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Query ("SELECT * FROM exercises")
    fun getExercises(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE id = :id")
    suspend fun getExercise(id: Int): ExerciseEntity?
    
    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)

    @Upsert
    suspend fun upsertExercise(exercise: ExerciseEntity)
}