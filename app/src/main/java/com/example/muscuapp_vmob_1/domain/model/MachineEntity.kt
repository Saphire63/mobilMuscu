package com.example.muscuapp_vmob_1.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "machines")
data class MachineEntity (
    @PrimaryKey(true) val id: Int? = null,
    val name: String ="",
    val max: Int = 0,
    val percentage: Int = 0,
    val weight: Int= 0,
    val description: String = "",
)