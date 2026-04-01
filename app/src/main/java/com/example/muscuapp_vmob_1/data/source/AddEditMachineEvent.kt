package com.example.muscuapp_vmob_1.data.source

sealed interface AddEditMachineEvent {
    data class EnteredId(val id: Int): AddEditMachineEvent
    data class EnteredName(val name: String): AddEditMachineEvent
    data class EnteredMax(val max: Int): AddEditMachineEvent
    data class EnteredDescription(val description: String): AddEditMachineEvent
    data object StoryDone: AddEditMachineEvent
    data object SaveStory: AddEditMachineEvent
}