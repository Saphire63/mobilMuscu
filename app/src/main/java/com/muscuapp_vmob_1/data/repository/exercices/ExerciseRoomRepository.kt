package com.muscuapp_vmob_1.data.repository.exercices

import com.muscuapp_vmob_1.data.source.ExerciseDao
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM.Companion.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class ExerciseRoomRepository @Inject constructor(
    private val dao: ExerciseDao
) : ExerciseRepository {
    override fun getExercises(): Flow<List<ExerciseVM>> {
        return dao.getExercises().map { entities ->
            entities.map { ExerciseVM.Companion.fromEntity(it) }
        }
    }

    override suspend fun getExercise(id: Int): ExerciseVM? {
        return dao.getExercise(id)?.let { ExerciseVM.Companion.fromEntity(it) }
    }

    override suspend fun upsertExercise(exercise: ExerciseVM) {
        dao.upsertExercise(exercise.toEntity())
    }

    override suspend fun deleteExercise(exercise: ExerciseVM) {
        dao.deleteExercise(exercise.toEntity())
    }
}