package com.example.muscuapp_vmob_1.data.repository.exercices

import android.content.Context
import com.example.muscuapp_vmob_1.domain.model.ExerciseEntity
import com.example.muscuapp_vmob_1.domain.model.ExerciseEntity.Companion.toVM
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ExerciseFichierRepository @Inject constructor (
    @ApplicationContext private val context: Context
) : ExerciseRepository {

    override fun getExercises(): Flow<List<ExerciseVM>> = flow {
        val jsonString = context.assets
            .open("exercises.json")
            .bufferedReader()
            .use { it.readText() }

        val exercises = Json.decodeFromString<List<ExerciseEntity>>(jsonString)
            .map{ it.toVM() }

        emit(exercises)
    }

    override suspend fun getExercise(id: Int): ExerciseVM? {
        TODO("Not yet implemented")
    }

    override suspend fun upsertExercise(exercise: ExerciseVM) {
        TODO("Not yet implemented")
    }


    override suspend fun deleteExercise(exercise: ExerciseVM) {
        throw Exception("pas implémenté")
    }
}