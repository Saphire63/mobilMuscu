package com.muscuapp_vmob_1.data.repository.exercices

import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    fun getExercises(): Flow<List<ExerciseVM>>

    suspend fun getExercise(id: Int): ExerciseVM?

    suspend fun upsertExercise(exercise: ExerciseVM)

    suspend fun deleteExercise(exercise: ExerciseVM)
}