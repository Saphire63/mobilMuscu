package com.example.muscuapp_vmob_1.data.source.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.muscuapp_vmob_1.domain.model.TrainingSegmentsEntity
import com.example.muscuapp_vmob_1.domain.model.TrainingEntity

data class TrainingWithSegments (
    @Embedded val training: TrainingEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
    )
    val segments: List<TrainingSegmentsEntity> // liste de segments avec leurs exercices associés
)