package com.example.muscuapp_vmob_1.ui.viewmodel

import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.MachineVM
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muscuapp_vmob_1.data.repository.exercices.MachineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ListExerciceViewModel @Inject constructor(
    private val repository: MachineRepository
) : ViewModel() {

    val machines: StateFlow<List<MachineVM>> = repository.getMachine()
        .map { list -> list.map { MachineVM.fromEntity(it) } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}