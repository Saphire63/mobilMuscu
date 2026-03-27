package com.example.muscuapp_vmob_1.ui.viewmodel

import android.util.Log
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.MachineVM
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muscuapp_vmob_1.data.repository.exercices.MachineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class ListExerciceViewModel @Inject constructor(
    private val repository: MachineRepository
) : ViewModel() {

    val machines: StateFlow<List<MachineVM>> = repository.getMachines()
        .catch { e -> Log.e("VM Crash", "Erreur Flow DB", e) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}