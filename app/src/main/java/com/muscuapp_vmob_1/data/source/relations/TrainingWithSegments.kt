package com.muscuapp_vmob_1.data.source.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.muscuapp_vmob_1.domain.model.TrainingSegmentsEntity
import com.muscuapp_vmob_1.domain.model.TrainingEntity

data class TrainingWithSegments (
    @Embedded val training: TrainingEntity,
    @Relation(
        entity = TrainingSegmentsEntity::class,
        parentColumn = "id",
        entityColumn = "trainingId",
    )
    val segments: List<SegmentWithExercises> // liste de segments avec leurs exercices associés
)