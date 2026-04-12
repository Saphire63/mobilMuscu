package com.example.muscuapp_vmob_1.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "exercises")
data class ExerciseEntity (
    @PrimaryKey(true) val id: Int? = null,
    val name: String ="",
    val max: Float? = null,
    val description: String = "",
    val imageUri: String?

    ){
    companion object{

    }
}