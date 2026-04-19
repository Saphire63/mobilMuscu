package com.muscuapp_vmob_1.ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muscuapp_vmob_1.domain.use_cases.exercise.AddEditExerciseEvent
import com.muscuapp_vmob_1.domain.use_cases.exercise.UpsertExerciseUseCase
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class AddEditExerciseViewModel @Inject constructor(
    private val upsertExerciseUseCase: UpsertExerciseUseCase,
    @ApplicationContext private val context: Context
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
            is AddEditExerciseEvent.EnteredName -> {
                _exercise.value = _exercise.value.copy(name = event.name)
            }
            is AddEditExerciseEvent.EnteredMax -> {
                _exercise.value = _exercise.value.copy(max = event.max)
            }
            is AddEditExerciseEvent.EnteredDescription -> {
                _exercise.value = _exercise.value.copy(description = event.description)
            }
            is AddEditExerciseEvent.EnteredImageUri -> {
                _exercise.value = _exercise.value.copy(imageUri = event.uri)
            }
            is AddEditExerciseEvent.UpdateImageUri -> {
                viewModelScope.launch {
                    val internalPath = saveImageToInternalStorage(event.uri)
                    _exercise.value = _exercise.value.copy(imageUri = internalPath)
                    // On sauvegarde immédiatement pour la carte
                    upsertExerciseUseCase(_exercise.value)
                }
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

    private fun saveImageToInternalStorage(uri: Uri?): String? {
        if (uri == null) return null
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val fileName = "exercise_${System.currentTimeMillis()}.jpg"
            val file = File(context.filesDir, fileName)

            inputStream?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}