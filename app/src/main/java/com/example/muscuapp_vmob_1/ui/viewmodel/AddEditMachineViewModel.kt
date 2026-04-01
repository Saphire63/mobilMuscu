package com.example.muscuapp_vmob_1.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muscuapp_vmob_1.data.repository.exercices.MachineRepository
import com.example.muscuapp_vmob_1.data.source.AddEditMachineEvent
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.MachineVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditMachineViewModel @Inject constructor(
    private val repository: MachineRepository
): ViewModel() {
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
                if(_machine.value.isDone) {
                    viewModelScope.launch {
                        repository.deleteMachine(_machine.value)
                    }
                }
            }
        }
    }
}