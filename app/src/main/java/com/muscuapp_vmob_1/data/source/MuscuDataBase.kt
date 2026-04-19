package com.muscuapp_vmob_1.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muscuapp_vmob_1.domain.model.ExerciseEntity
import com.muscuapp_vmob_1.domain.model.TrainingEntity
import com.muscuapp_vmob_1.domain.model.TrainingSegmentsEntity

@Database(
    entities = [ExerciseEntity::class, TrainingEntity::class, TrainingSegmentsEntity::class],
    version = 8,
    exportSchema = false
)
abstract class MuscuDataBase : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun trainingDao(): TrainingDao
    abstract fun trainingSegmentsDao(): TrainingSegmentsDao

    companion object {
        const val DATABASE_NAME = "muscu.db"
    }
}
