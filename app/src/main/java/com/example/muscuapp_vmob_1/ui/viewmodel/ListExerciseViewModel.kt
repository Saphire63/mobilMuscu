package com.example.muscuapp_vmob_1.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muscuapp_vmob_1.domain.use_cases.DeleteExerciseUseCase
import com.example.muscuapp_vmob_1.domain.use_cases.GetExercisesUseCase
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseUiState
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListExerciseViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    val exercises: StateFlow<ExerciseUiState> = combine(
        getExercisesUseCase(),
        _searchQuery
    ) { list, query ->
        val filteredList = if (query.isEmpty()) {
            list
        } else {
            list.filter { it.name.contains(query, ignoreCase = true) }
        }

        if (filteredList.isEmpty()) {
            ExerciseUiState.Empty
        } else {
            ExerciseUiState.Success(filteredList)
        }
    }
    .catch { e ->
        Log.e("VM Crash", "Erreur Flow UseCase", e)
        emit(ExerciseUiState.Error("Erreur de chargement"))
    }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ExerciseUiState.Loading
    )

    fun deleteExercise(exerciseVM: ExerciseVM){
        viewModelScope.launch {
            deleteExerciseUseCase(exerciseVM)
        }
    }
}
