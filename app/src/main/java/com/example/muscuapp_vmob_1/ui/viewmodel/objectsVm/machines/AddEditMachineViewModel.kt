package com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.muscuapp_vmob_1.data.source.AddEditMachineEvent
import androidx.compose.runtime.State

class AddEditMachineViewModel : ViewModel() {
    private val _machine = mutableStateOf(MachineVM())
    val machine : State<MachineVM> = _machine

    fun onEvent(event: AddEditMachineEvent) {
        //TODO
    }
}