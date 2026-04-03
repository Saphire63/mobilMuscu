package com.example.muscuapp_vmob_1.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muscuapp_vmob_1.domain.use_cases.DeleteMachineUseCase
import com.example.muscuapp_vmob_1.domain.use_cases.GetMachinesUseCase
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.MachineUiState
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.MachineVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListExerciceViewModel @Inject constructor(
    private val getMachinesUseCase: GetMachinesUseCase,
    private val deleteMachineUseCase: DeleteMachineUseCase
) : ViewModel() {

    val machines: StateFlow<MachineUiState> = getMachinesUseCase()
        .map { list ->
            if (list.isEmpty()) {
                MachineUiState.Empty
            } else {
                MachineUiState.Success(list)
            }
        }
        .catch { e ->
            Log.e("VM Crash", "Erreur Flow UseCase", e)
            emit(MachineUiState.Error("Erreur de chargement"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MachineUiState.Loading
        )

    fun deleteMachine(machineVM: MachineVM){
        viewModelScope.launch {
            deleteMachineUseCase(machineVM)
        }
    }
}