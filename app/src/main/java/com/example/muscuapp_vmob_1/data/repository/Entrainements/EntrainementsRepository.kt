package com.example.muscuapp_vmob_1.data.repository.Entrainements

import com.example.muscuapp_vmob_1.model.Entrainements

interface EntrainementsRepository {
    fun getEntrainements(): List<Entrainements>
    fun addEntrainements(e: Entrainements): Int
    fun deleteEntrainements(id: Int): Int
}