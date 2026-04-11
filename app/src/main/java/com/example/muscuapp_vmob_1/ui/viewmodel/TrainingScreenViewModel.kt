package com.example.muscuapp_vmob_1.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.muscuapp_vmob_1.domain.use_cases.training.DeleteTrainingUseCase
import com.example.muscuapp_vmob_1.domain.use_cases.training.GetTrainingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class TrainingScreenViewModel @Inject constructor(
    private val getTrainingsUseCase: GetTrainingsUseCase,
    private val deleteTrainingUseCase: DeleteTrainingUseCase
): ViewModel() {
    private val _searchQuery =  MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun onSearchQueryChange(query: String){
        _searchQuery.value = query
    }

}