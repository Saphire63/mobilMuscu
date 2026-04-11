package com.example.muscuapp_vmob_1.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muscuapp_vmob_1.domain.use_cases.training.AddEditTrainingEvent
import com.example.muscuapp_vmob_1.domain.use_cases.training.UpsertTrainingUseCase
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTrainingViewModel @Inject constructor(
    private val upsertTrainingUseCase: UpsertTrainingUseCase
): ViewModel(){

    private  val _training = mutableStateOf(TrainingVM())
    val training: State<TrainingVM> = _training

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