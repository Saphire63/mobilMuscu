package com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines

sealed class MachineUiState {
    object Loading : MachineUiState()
    object Empty : MachineUiState()
    data class Success(val machines: List<MachineVM>) : MachineUiState()
    data class Error(val message: String) : MachineUiState()
}