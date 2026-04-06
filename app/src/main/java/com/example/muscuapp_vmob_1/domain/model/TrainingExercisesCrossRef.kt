package com.example.muscuapp_vmob_1.domain.model

import androidx.room.Entity

@Entity(
    primaryKeys = ["trainingId","exerciseId"]
)
data class TrainingExercisesCrossRef(
    val trainingId: Int,
    val exerciseId: Int,
)