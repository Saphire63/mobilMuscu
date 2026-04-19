package com.muscuapp_vmob_1.domain.exeptions

sealed class ExerciseException(message: String) : Exception(message) {
    class NameEmptyException(message: String) : ExerciseException(message)
    class NotDoneException(message: String) : ExerciseException(message)
}