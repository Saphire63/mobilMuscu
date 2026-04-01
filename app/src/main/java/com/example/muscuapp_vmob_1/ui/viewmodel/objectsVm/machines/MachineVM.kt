package com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines

import com.example.muscuapp_vmob_1.domain.model.MachineEntity

class MachineVM (
    val id: Int = 0,
    val name: String ="",
    val max: Int = 0,
    val description: String = ""
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