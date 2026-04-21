package com.muscuapp_vmob_1.data.notifications

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimerManager @Inject constructor() {
    private val _runningTrainingId = MutableStateFlow<Int?>(null)
    val runningTrainingId: StateFlow<Int?> = _runningTrainingId

    fun startTimer(trainingId: Int) {
        _runningTrainingId.value = trainingId
    }

    fun stopTimer() {
        _runningTrainingId.value = null
    }
}
