package com.example.muscuapp_vmob_1.data

sealed interface AddEditMachineEvent {
    data class EnteredId(val id: Int): AddEditMachineEvent
    data class EnteredName(val name: String): AddEditMachineEvent
    data class EnteredMax(val max: Float?): AddEditMachineEvent
    data class EnteredDescription(val description: String): AddEditMachineEvent
    data object MachineDone: AddEditMachineEvent
    data object SaveMachine: AddEditMachineEvent
}