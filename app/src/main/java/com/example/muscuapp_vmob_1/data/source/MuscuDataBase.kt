package com.example.muscuapp_vmob_1.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.muscuapp_vmob_1.domain.model.ExerciseEntity

@Database(entities = [ExerciseEntity::class], version = 6, exportSchema = false)
abstract class MuscuDataBase : RoomDatabase() {
    abstract fun dao(): ExerciseDao
    companion object {
        const val DATABASE_NAME = "muscu.db"
    }
}
