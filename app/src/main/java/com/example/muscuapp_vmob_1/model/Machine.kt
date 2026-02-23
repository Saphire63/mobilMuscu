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

//ici j'ai utilisé Gemini pour m'aider à comprendre la fonction de loading,
// et de changer quelques lignes dans le fichier app.gradle.kts et libs.versions.toml
fun  deserializeMachinesFromJson(context: Context): List<Machine> {
    val jsonString = context.assets.open("machines.json").bufferedReader().use { it.readText() }
    return Json.decodeFromString<List<Machine>>(jsonString)
}
