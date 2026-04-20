package com.muscuapp_vmob_1.domain.use_cases.exercise.data

import com.muscuapp_vmob_1.data.repository.exercices.ExerciseRepository
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import kotlinx.coroutines.flow.Flow

class FakeExerciseRepository : ExerciseRepository {

    val exercises = mutableListOf<ExerciseVM>()
    override fun getExercises(): Flow<List<ExerciseVM>> {
        TODO("Not yet implemented")
    }

    override suspend fun getExercise(id: Int): ExerciseVM? {
        TODO("Not yet implemented")
    }

    override suspend fun upsertExercise(exercise: ExerciseVM) {
        exercises.removeIf { it.id == exercise.id }
        exercises.add(exercise)
    }

    override suspend fun deleteExercise(exercise: ExerciseVM) {
        TODO("Not yet implemented")
    }

}