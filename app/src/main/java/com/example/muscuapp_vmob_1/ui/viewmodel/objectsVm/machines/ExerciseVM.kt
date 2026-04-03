package com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines

import com.example.muscuapp_vmob_1.domain.model.ExerciseEntity
import kotlinx.serialization.Serializable


@Serializable
data class ExerciseVM (
    val id: Int = 0,
    val name: String ="",
    val max: Float? = null,
    val description: String = "",
    val isDone: Boolean = false // ne sert que pour le formulaire d'ajout et d'édit
){
    companion object{
        fun fromEntity(exercise: ExerciseEntity): ExerciseVM{
            return ExerciseVM(
                name= exercise.name,
                max= exercise.max,
                description = exercise.description
            )
        }

        fun ExerciseVM.toEntity(): ExerciseEntity {
            return ExerciseEntity(
                id = if (id == 0) null else id,
                name = name,
                max = max,
                description = description
            )
        }
    }
}