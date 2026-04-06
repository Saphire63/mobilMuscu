package com.example.muscuapp_vmob_1.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muscuapp_vmob_1.data.AddEditExerciseEvent
import com.example.muscuapp_vmob_1.domain.use_cases.exercise.UpsertExerciseUseCase
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditExerciseViewModel @Inject constructor(
    private val upsertExerciseUseCase: UpsertExerciseUseCase
): ViewModel() {
    private val _exercise = mutableStateOf(ExerciseVM())
    val exercise : State<ExerciseVM> = _exercise

    private val _error = mutableStateOf<Boolean>(false)
    val error : State<Boolean> = _error

    fun clearError() {
        _error.value = false
    }

    fun onEvent(event: AddEditExerciseEvent) {
        when (event) {
            is AddEditExerciseEvent.EnteredId -> {
                _exercise.value = _exercise.value.copy(id = event.id)
            }
            is AddEditExerciseEvent.EnteredName -> {
                _exercise.value = _exercise.value.copy(name = event.name)
            }
            is AddEditExerciseEvent.EnteredMax -> {
                _exercise.value = _exercise.value.copy(max = event.max)
            }
            is AddEditExerciseEvent.EnteredDescription -> {
                _exercise.value = _exercise.value.copy(description = event.description)
            }
            AddEditExerciseEvent.ExerciseDone ->
                _exercise.value = _exercise.value.copy(isDone = !_exercise.value.isDone)

            is AddEditExerciseEvent.LoadExercise -> {
                _exercise.value = event.exercise
            }

            AddEditExerciseEvent.ResetForm -> {
                _exercise.value = ExerciseVM()
            }

            AddEditExerciseEvent.SaveExercise -> {
                viewModelScope.launch {
                    upsertExerciseUseCase(_exercise.value)
                }
            }
        }
    }
}