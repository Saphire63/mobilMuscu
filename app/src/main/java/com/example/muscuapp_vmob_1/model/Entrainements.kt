package com.example.muscuapp_vmob_1.model

import kotlinx.serialization.Serializable

@Serializable
data class Entrainements(
    val category: String="",
    val machines: List<Machine>,


    )
