package com.example.muscuapp_vmob_1.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muscuapp_vmob_1.domain.use_cases.training.AddEditTrainingEvent
import com.example.muscuapp_vmob_1.domain.use_cases.training.GetTrainingUseCase
import com.example.muscuapp_vmob_1.domain.use_cases.training.UpsertTrainingUseCase
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.muscuapp_vmob_1.domain.use_cases.exercise.GetExercisesUseCase
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTrainingViewModel @Inject constructor(
    private val upsertTrainingUseCase: UpsertTrainingUseCase,
    private val getExercisesUseCase: GetExercisesUseCase,
    private val getTrainingUseCase: GetTrainingUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){

    private  val _training = mutableStateOf(TrainingVM())
    val training: State<TrainingVM> = _training

    private val _availableExercises = MutableStateFlow<List<ExerciseVM>>(emptyList())
    val availableExercises: StateFlow<List<ExerciseVM>> = _availableExercises.asStateFlow()

    init {
        val trainingId = savedStateHandle.get<Int>("trainingId") ?: -1
        if (trainingId != -1) {
            viewModelScope.launch {
                getTrainingUseCase(trainingId)?.let { loadedTraining ->
                    _training.value = loadedTraining
                }
            }
        }
        getExercises()
    }

    private fun getExercises() {
        getExercisesUseCase()
            .onEach { exercises ->
                _availableExercises.value = exercises
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: AddEditTrainingEvent){
        when(event){
            is AddEditTrainingEvent.EnteredDescription -> {
                _training.value = _training.value.copy(description = event.description)
            }
            is AddEditTrainingEvent.EnteredName -> {
                _training.value = _training.value.copy(name = event.name)
            }
            is AddEditTrainingEvent.EnteredType -> {
                _training.value = _training.value.copy(type = event.type)
            }
            is AddEditTrainingEvent.LoadTraining -> {
                _training.value = event.training
            }
            is AddEditTrainingEvent.AddExercise -> {
                val updatedExercises = _training.value.exercises.toMutableList().apply {
                    add(event.exercise)
                }
                _training.value = _training.value.copy(exercises = updatedExercises)
            }
            is AddEditTrainingEvent.RemoveExerciseAt -> {
                val updatedExercises = _training.value.exercises.toMutableList().apply {
                    if (event.index in indices) {
                        removeAt(event.index)
                    }
                }
                _training.value = _training.value.copy(exercises = updatedExercises)
            }
            is AddEditTrainingEvent.MoveExercise -> {
                val updatedExercises = _training.value.exercises.toMutableList().apply {
                    if (event.fromIndex in indices && event.toIndex in indices) {
                        val exercise = removeAt(event.fromIndex)
                        add(event.toIndex, exercise)
                    }
                }
                _training.value = _training.value.copy(exercises = updatedExercises)
            }
            AddEditTrainingEvent.ResetForm -> {
                _training.value = TrainingVM()
            }
            AddEditTrainingEvent.SaveTraining -> {
                viewModelScope.launch {
                    upsertTrainingUseCase(_training.value)
                }
            }
        }
    }
}