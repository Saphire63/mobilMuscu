package com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training

import com.example.muscuapp_vmob_1.domain.model.TrainingEntity
import com.example.muscuapp_vmob_1.domain.model.ExerciseEntity
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import kotlinx.serialization.Serializable

@Serializable
data class TrainingVM (
    val id: Int = 0 ,
    val name: String = "",
    val type: String = "",
    val description: String = "",
    val exercises: List<ExerciseVM> = emptyList()
) {
    fun toEntity(): TrainingEntity {
        return TrainingEntity(
            id = if (id == 0) null else id,
            name = name,
            type = type,
            description = description
        )
    }

    companion object {
        fun fromEntity(trainingEntity: TrainingEntity, exercises: List<ExerciseEntity> = emptyList()): TrainingVM {
            return TrainingVM(
                id = trainingEntity.id ?: 0,
                name = trainingEntity.name,
                type = trainingEntity.type,
                description = trainingEntity.description,
                exercises = exercises.map { ExerciseVM.fromEntity(it) }
            )
        }
    }
}