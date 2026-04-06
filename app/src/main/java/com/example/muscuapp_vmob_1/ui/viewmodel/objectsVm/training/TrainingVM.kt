package com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training

import com.example.muscuapp_vmob_1.domain.model.TrainingEntity
import kotlinx.serialization.Serializable

@Serializable
data class TrainingVM (
    val id: Int,
    val name: String,
    val type: String,
    val description: String,
){
    companion object{
        fun TrainingVM.toEntity(): TrainingEntity{
            return TrainingEntity(
                id = id,
                name = name,
                type = type,
                description = description
            )
        }
    }
}