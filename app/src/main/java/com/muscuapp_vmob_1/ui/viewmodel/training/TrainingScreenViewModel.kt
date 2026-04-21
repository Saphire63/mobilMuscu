package com.muscuapp_vmob_1.ui.viewmodel.training

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muscuapp_vmob_1.data.notifications.TimerManager
import com.muscuapp_vmob_1.data.notifications.TimerService
import com.muscuapp_vmob_1.domain.use_cases.training.DeleteTrainingUseCase
import com.muscuapp_vmob_1.domain.use_cases.training.GetTrainingsUseCase
import com.muscuapp_vmob_1.domain.use_cases.training.UpsertTrainingUseCase
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingUiState
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.training.TrainingVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingScreenViewModel @Inject constructor(
    private val getTrainingsUseCase: GetTrainingsUseCase,
    private val deleteTrainingUseCase: DeleteTrainingUseCase,
    private val upsertTrainingUseCase: UpsertTrainingUseCase,
    private val timerManager: TimerManager
): ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val runningTrainingId: StateFlow<Int?> = timerManager.runningTrainingId

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun deleteTraining(training: TrainingVM) {
        viewModelScope.launch {
            deleteTrainingUseCase(training)
        }
    }

    fun unpinTraining(training: TrainingVM) {
        val updatedTraining = training.copy(isFavorite = false)
        viewModelScope.launch {
            upsertTrainingUseCase(updatedTraining)
        }
    }

    fun toggleTimer(context: Context, training: TrainingVM) {
        val isRunning = timerManager.runningTrainingId.value == training.id
        val intent = Intent(context, TimerService::class.java)

        if (isRunning) {
            intent.action = TimerService.ACTION_STOP
            context.startService(intent)
        } else {
            intent.putExtra(TimerService.EXTRA_TRAINING_NAME, training.name)
            intent.putExtra(TimerService.EXTRA_TRAINING_ID, training.id)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }

    val trainings: StateFlow<TrainingUiState> = combine(
        getTrainingsUseCase(),
        _searchQuery
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
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TrainingUiState.Loading
    )
}
