package com.example.muscuapp_vmob_1.di

import android.content.Context
import com.example.muscuapp_vmob_1.data.repository.Exercices.MachineFichierRepository
import com.example.muscuapp_vmob_1.data.repository.Exercices.MachinesRepository
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
    fun provideMachineRepository(
        @ApplicationContext context: Context
    ): MachinesRepository {
        return MachineFichierRepository(context)
    }
}