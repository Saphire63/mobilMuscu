package com.example.muscuapp_vmob_1.data.source.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.muscuapp_vmob_1.domain.model.TrainingSegmentsEntity
import com.example.muscuapp_vmob_1.domain.model.ExerciseEntity

data class SegmentWithExercises(
    @Embedded val segments: TrainingSegmentsEntity,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "id",
    )
    val exercises: ExerciseEntity //exercice associé
)