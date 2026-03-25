package com.example.muscuapp_vmob_1.model

import kotlinx.serialization.Serializable

@Serializable
data class Machine(
    val name: String ="",
    val max: Int = 0,
    val percentage: Int = 0,
    val weight: Int= 0,
    val description: String = "",
)
