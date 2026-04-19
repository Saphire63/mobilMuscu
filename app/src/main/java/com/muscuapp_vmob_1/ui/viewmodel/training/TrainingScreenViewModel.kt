package com.muscuapp_vmob_1.ui.viewmodel.training
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muscuapp_vmob_1.domain.use_cases.training.DeleteTrainingUseCase
import com.muscuapp_vmob_1.domain.use_cases.training.GetTrainingsUseCase
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingUiState
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import com.muscuapp_vmob_1.domain.use_cases.training.UpsertTrainingUseCase

import kotlinx.coroutines.launch


@HiltViewModel
class TrainingScreenViewModel @Inject constructor(
    private val getTrainingsUseCase: GetTrainingsUseCase,
    private val deleteTrainingUseCase: DeleteTrainingUseCase,
    private val upsertTrainingUseCase: UpsertTrainingUseCase
): ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun onSearchQueryChange(query: String){
        _searchQuery.value = query
    }

    fun deleteTraining(training: TrainingVM) {
        viewModelScope.launch {
            deleteTrainingUseCase(training)
        }
    }

    fun unpinTraining(training: TrainingVM) {
        // Crée une copie de l'entraînement avec le statut modifié
        val updatedTraining = training.copy(isFavorite = false)

        viewModelScope.launch {
            upsertTrainingUseCase(updatedTraining)
        }
    }
    
    val trainings: StateFlow<TrainingUiState> = combine(
        flow = getTrainingsUseCase(),
        flow2 = _searchQuery
    ) { list, query ->
        val filteredList = if (query.isEmpty()) {
            list
        } else {
            list.filter { it.name.contains(query, ignoreCase = true) }
        }

        if (filteredList.isEmpty()) TrainingUiState.Empty
        else TrainingUiState.Success(filteredList)
    }
    .catch { e ->
        Log.e("VM crash", "Erreur Flow Use Case ", e)
        emit(TrainingUiState.Error("Erreur chargement bdd"))
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = TrainingUiState.Loading
        )

}