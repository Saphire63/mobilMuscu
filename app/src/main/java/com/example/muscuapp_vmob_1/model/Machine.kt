package com.example.muscuapp_vmob_1.model

data class Machine(
    val name: String ="",
    val max: Int = 0,
    val percentage: Int = 0,
    val weight: Int= 0,
    val description: String = "",
)
val machines = listOf(
    Machine(
    "Bench",
    100,
    70,
    70,
    "Machine pour travailler les pectoraux."
    ),
    Machine(
    "Machine 2",
    50,
    90,
    45,
    "Exercice secondaire."
    ),
    Machine(
    "Machine 3",
    200,
    0,
    0,
    "Pas encore utilisée."
    )
)