package com.muscuapp_vmob_1.domain.use_cases.exercise

import com.muscuapp_vmob_1.data.repository.exercices.ExerciseRepository
import com.muscuapp_vmob_1.domain.exeptions.ExerciseException
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import javax.inject.Inject

class UpsertExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    @Throws(ExerciseException::class)
    suspend operator fun invoke(exercise: ExerciseVM) {
        if (exercise.name.isEmpty()){
            throw ExerciseException.NameEmptyException("Champs name obligatoire ")
        }
        if (!exercise.isDone){
            throw ExerciseException.NotDoneException("Veuillez cocher Terminé")
        }
        repository.upsertExercise(exercise)
    }
}