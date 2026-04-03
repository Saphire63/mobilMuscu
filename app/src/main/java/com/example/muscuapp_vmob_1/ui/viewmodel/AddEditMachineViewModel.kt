package com.example.muscuapp_vmob_1.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muscuapp_vmob_1.data.AddEditMachineEvent
import com.example.muscuapp_vmob_1.domain.use_cases.UpsertMachineUseCase
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.MachineVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditMachineViewModel @Inject constructor(
    private val upsertMachineUseCase: UpsertMachineUseCase
): ViewModel() {
    private val _machine = mutableStateOf(MachineVM())
    val machine : State<MachineVM> = _machine

    private val _error = mutableStateOf<Boolean>(false)
    val error : State<Boolean> = _error

    fun clearError() {
        _error.value = false
    }

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

            is AddEditMachineEvent.LoadMachine -> {
                _machine.value = event.machine
            }
            
            AddEditMachineEvent.ResetForm -> {
                _machine.value = MachineVM()
            }

            AddEditMachineEvent.SaveMachine -> {
                viewModelScope.launch {
                    upsertMachineUseCase(_machine.value)
                }
            }
        }
    }
}