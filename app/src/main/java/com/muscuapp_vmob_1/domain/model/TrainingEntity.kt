package com.muscuapp_vmob_1.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "training")
data class TrainingEntity (
    @PrimaryKey(true) val id: Int? = null,
    val name: String,
    val type: String,
    val description: String,
){
    companion object{
    }
}