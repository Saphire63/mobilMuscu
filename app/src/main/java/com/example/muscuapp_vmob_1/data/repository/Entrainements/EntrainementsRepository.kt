package com.example.muscuapp_vmob_1.data.repository.Entrainements

import com.example.muscuapp_vmob_1.model.Entrainements
import kotlinx.coroutines.flow.Flow

interface EntrainementsRepository {
    fun getEntrainements(): Flow<List<Entrainements>>
    fun addEntrainements(e: Entrainements): Int
    fun deleteEntrainements(id: Int): Int
}