package com.example.muscuapp_vmob_1.model

import kotlinx.serialization.Serializable
import android.content.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class Machine(
    val name: String ="",
    val max: Int = 0,
    val percentage: Int = 0,
    val weight: Int= 0,
    val description: String = "",
)
