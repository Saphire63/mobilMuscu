package com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.muscuapp_vmob_1.data.source.AddEditMachineEvent
import androidx.compose.runtime.State

class AddEditMachineViewModel : ViewModel() {
    private val _machine = mutableStateOf(MachineVM())
    val machine : State<MachineVM> = _machine

    fun onEvent(event: AddEditMachineEvent) {
        when (event) {
            is AddEditMachineEvent.EnteredId -> {
                _machine.value = _machine.value.copy(id = event.id)
            }
            is AddEditMachineEvent.EnteredName -> {
                _machine.value = _machine.value.copy(name = event.name)
            }
            is AddEditMachineEvent.EnteredMax -> {
                _machine.value = _machine.value.copy(max = event.max)
            }
            is AddEditMachineEvent.EnteredDescription -> {
                _machine.value = _machine.value.copy(description = event.description)
            }
            AddEditMachineEvent.MachineDone ->
                _machine.value = _machine.value.copy(isDone = !_machine.value.isDone)


            AddEditMachineEvent.SaveMachine -> {
                //addOrUpdateStory(machine.value)
                //TODO
            }
        }
    }
}