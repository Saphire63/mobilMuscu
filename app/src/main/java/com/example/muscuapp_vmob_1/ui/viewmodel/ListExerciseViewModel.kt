package com.example.muscuapp_vmob_1.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muscuapp_vmob_1.domain.use_cases.DeleteExerciseUseCase
import com.example.muscuapp_vmob_1.domain.use_cases.GetExercisesUseCase
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.ExerciseUiState
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.ExerciseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListExerciseViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase
) : ViewModel() {

    val exercises: StateFlow<ExerciseUiState> = getExercisesUseCase()
        .map { list ->
            if (list.isEmpty()) {
                ExerciseUiState.Empty
            } else {
                ExerciseUiState.Success(list)
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