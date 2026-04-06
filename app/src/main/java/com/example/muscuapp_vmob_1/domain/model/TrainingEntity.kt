package com.example.muscuapp_vmob_1.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM

@Entity(tableName = "training")
data class TrainingEntity (
    @PrimaryKey(true) val id: Int? = null,
    val name: String,
    val type: String,
    val description: String,
){
    companion object{
        fun TrainingEntity.toVM(): TrainingVM {
            return TrainingVM(
                id = id ?: 0,
                name = name,
                type = type,
                description = description

            )
        }
    }
}