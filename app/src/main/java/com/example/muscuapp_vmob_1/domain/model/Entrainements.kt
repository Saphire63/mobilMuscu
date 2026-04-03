package com.example.muscuapp_vmob_1.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Entrainements(
    val category: String="",
    val exercises: List<ExerciseEntity>,
)