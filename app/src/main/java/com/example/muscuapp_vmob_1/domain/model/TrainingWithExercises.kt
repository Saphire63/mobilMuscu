package com.example.muscuapp_vmob_1.domain.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TrainingWithExercises (
    @Embedded val training: TrainingEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(TrainingExercisesCrossRef::class)
    )
    val trainings: List<TrainingEntity>
)