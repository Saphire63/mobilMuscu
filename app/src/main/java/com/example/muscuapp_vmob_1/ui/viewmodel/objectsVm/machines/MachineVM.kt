package com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines

import com.example.muscuapp_vmob_1.domain.model.MachineEntity
import kotlinx.serialization.Serializable


@Serializable
data class MachineVM (
    val id: Int = 0,
    val name: String ="",
    val max: Float? = null,
    val description: String = "",
    val isDone: Boolean = false // ne sert que pour le formulaire d'ajout et d'édit
){
    companion object{
        fun fromEntity(machine: MachineEntity): MachineVM{
            return MachineVM(
                name= machine.name,
                max= machine.max,
                description = machine.description
            )
        }

        fun MachineVM.toEntity(): MachineEntity {
            return MachineEntity(
                id = if (id == 0) null else id,
                name = name,
                max = max,
                description = description
            )
        }
    }
}