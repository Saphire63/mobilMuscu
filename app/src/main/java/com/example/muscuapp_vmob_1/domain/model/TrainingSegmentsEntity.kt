package com.example.muscuapp_vmob_1.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "training_segment",
    foreignKeys = [
        ForeignKey(
            entity = TrainingEntity::class,
            parentColumns = ["id"],
            childColumns = ["trainingId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]

)
data class TrainingSegmentsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val trainingId: Int,
    val exerciseId: Int,

    val order: Int
)