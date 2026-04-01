package com.example.muscuapp_vmob_1.di

import android.content.Context
import androidx.room.Room
import com.example.muscuapp_vmob_1.data.repository.exercices.MachineFichierRepository
import com.example.muscuapp_vmob_1.data.repository.exercices.MachineRepository
import com.example.muscuapp_vmob_1.data.repository.exercices.MachineRoomRepository
import com.example.muscuapp_vmob_1.data.source.MachineDao
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
        ).build()
    }

    @Provides
    @Singleton
    fun provideMachineDao(db: MuscuDataBase): MachineDao {
        return db.dao()
    }

    @Provides
    @Singleton
    fun provideMachineRepository(
        @ApplicationContext context: Context,
        dao: MachineDao
    ): MachineRepository {
        // Switch rapide :
        //return MachineFichierRepository(context)  // ← JSON
        return MachineRoomRepository(dao)           // ← Room
    }
}