package com.example.muscuapp_vmob_1.di

import android.content.Context
import androidx.room.Room
import com.example.muscuapp_vmob_1.data.repository.exercices.ExerciseFichierRepository
import com.example.muscuapp_vmob_1.data.repository.exercices.ExerciseRepository
import com.example.muscuapp_vmob_1.data.repository.exercices.ExerciseRoomRepository
import com.example.muscuapp_vmob_1.data.source.ExerciseDao
import com.example.muscuapp_vmob_1.data.source.MuscuDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMuscuDatabase(@ApplicationContext context: Context): MuscuDataBase {
        return Room.databaseBuilder(
            context,
            MuscuDataBase::class.java,
            MuscuDataBase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideExerciseDao(db: MuscuDataBase): ExerciseDao {
        return db.dao()
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(
        @ApplicationContext context: Context,
        dao: ExerciseDao
    ): ExerciseRepository {
        // Switch rapide :
        //return ExerciseFichierRepository(context)  // ← JSON
        return ExerciseRoomRepository(dao)           // ← Room
    }
}