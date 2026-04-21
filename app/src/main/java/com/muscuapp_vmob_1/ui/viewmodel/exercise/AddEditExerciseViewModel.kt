
package com.muscuapp_vmob_1.ui.viewmodel.exercise

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.muscuapp_vmob_1.domain.exeptions.ExerciseException
import com.muscuapp_vmob_1.domain.use_cases.exercise.AddEditExerciseEvent
import com.muscuapp_vmob_1.domain.use_cases.exercise.UpsertExerciseUseCase
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class AddEditExerciseViewModel @Inject constructor(
    private val upsertExerciseUseCase: UpsertExerciseUseCase,
    @ApplicationContext private val context: Context
): ViewModel() {

    // exercise
    private val _exercise = mutableStateOf(ExerciseVM())
    val exercise : State<ExerciseVM> = _exercise


    //error done
    private val _doneError = mutableStateOf<String?>(null)
    val doneError : State<String?> = _doneError


    fun clearError() {
        _doneError.value = null
        _nameError.value = null
    }


    // name error
    private val _nameError = mutableStateOf<String?>(null)
    val nameError: State<String?> = _nameError

    private val _eventFlow = MutableSharedFlow<AddEditExerciseUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                    try {
                        val internalPath = saveImageToInternalStorage(event.uri)
                        _exercise.value = _exercise.value.copy(imageUri = internalPath, isDone = true)
                        upsertExerciseUseCase(_exercise.value)
                        clearError()
                    } catch (e: ExerciseException) {
                        when (e) {
                            is ExerciseException.NameEmptyException -> {
                                _nameError.value = e.message
                            }
                            is ExerciseException.NotDoneException -> {
                                _doneError.value = e.message
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            AddEditExerciseEvent.ExerciseDone -> {
                _exercise.value = _exercise.value.copy(isDone = !_exercise.value.isDone)
                if (_exercise.value.isDone) {
                    clearError()
                }
            }

            is AddEditExerciseEvent.LoadExercise -> {
                _exercise.value = event.exercise
            }

            AddEditExerciseEvent.ResetForm -> {
                _exercise.value = ExerciseVM()
                clearError()

            }

            AddEditExerciseEvent.SaveExercise -> {
                viewModelScope.launch {
                    try {
                        upsertExerciseUseCase(_exercise.value)
                        clearError()
                        _eventFlow.emit(AddEditExerciseUiEvent.Saved)
                    } catch (e: ExerciseException) {
                        when (e) {
                            is ExerciseException.NameEmptyException -> {
                                _nameError.value = e.message
                                _doneError.value = null
                            }
                            is ExerciseException.NotDoneException -> {
                                _doneError.value = e.message
                                _nameError.value = null
                            }
                        }
                    }
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