package com.example.muscuapp_vmob_1.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.muscuapp_vmob_1.domain.use_cases.training.UpsertTrainingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditTrainingViewModel @Inject constructor(
    private val upsertTrainingUseCase: UpsertTrainingUseCase
): ViewModel(){

}