package com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm

import com.example.muscuapp_vmob_1.domain.model.Machine

class MachineVM (
    val name: String,
    val max: Int,
    val description: String
){
    companion object{
        fun fromEntity(machine: Machine): MachineVM{
            return MachineVM(
                name= machine.name,
                max= machine.max,
                description = machine.description
            )
        }
    }
}