package com.example.muscuapp_vmob_1.data.repository.exercices

import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.ExerciseVM
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    fun getExercises(): Flow<List<ExerciseVM>>

    suspend fun getExercise(id: Int): ExerciseVM?

    suspend fun upsertExercise(exercise: ExerciseVM)

    suspend fun deleteExercise(exercise: ExerciseVM)
}