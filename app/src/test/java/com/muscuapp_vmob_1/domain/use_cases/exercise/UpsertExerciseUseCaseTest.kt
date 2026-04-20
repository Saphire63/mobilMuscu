package com.muscuapp_vmob_1.domain.use_cases.exercise

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import com.muscuapp_vmob_1.domain.exeptions.ExerciseException
import com.muscuapp_vmob_1.domain.use_cases.exercise.UpsertExerciseUseCase
import com.muscuapp_vmob_1.domain.use_cases.exercise.data.FakeExerciseRepository
import com.muscuapp_vmob_1.ui.viewmodel.objectsVm.exercises.ExerciseVM
import kotlinx.coroutines.runBlocking

class UpsertExerciseUseCaseTest {

    lateinit var upsertExerciseUseCase: UpsertExerciseUseCase
    lateinit var repository: FakeExerciseRepository

    @Before
    fun setUp() {
        repository = FakeExerciseRepository()
        upsertExerciseUseCase = UpsertExerciseUseCase(repository)
    }

    @Test
    fun `exercise should be added if fields are valid`() {
        runBlocking {

            // Arrange
            val exercise = ExerciseVM(
                id = 1,
                name = "Bench Press",
                isDone = true
            )

            // Act
            upsertExerciseUseCase(exercise)

            // Assert
            assertEquals(1, repository.exercises.size)
            assertTrue(repository.exercises.contains(exercise))
        }
    }
    @Test(expected = ExerciseException.NameEmptyException::class)
    fun `should not add exercise if name is empty`() {
        runBlocking {
            //Arrange
            val exercise = ExerciseVM(
                id = 1,
                name = "",
                isDone = true
            )
            //Act
            upsertExerciseUseCase(exercise)
            //Assert dans le @Test()
        }
    }
    @Test(expected = ExerciseException.NotDoneException::class)
    fun `should not add exercise if not done`() {
        runBlocking {
            //Arrange
            val exercise = ExerciseVM(
                id = 1,
                name = "Bench",
                isDone = false
            )
            //Act
            upsertExerciseUseCase(exercise)
            //Assert dans le @Test()
        }
    }


}