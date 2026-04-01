package com.example.muscuapp_vmob_1.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.MachineVM
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "machines")
data class MachineEntity (
    @PrimaryKey(true) val id: Int? = null,
    val name: String ="",
    val max: Int = 0,
    val percentage: Int = 0,
    val weight: Int= 0,
    val description: String = "",

){
    companion object{
        fun MachineEntity.toVM(): MachineVM{
            return MachineVM(
                name= name,
                max= max,
                description = description

            )
        }
    }
}