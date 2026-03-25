package com.example.muscuapp_vmob_1.data.repository.Entrainements

import com.example.muscuapp_vmob_1.model.Entrainements
import kotlinx.coroutines.flow.Flow

class EntrainementsFichierRepository : EntrainementsRepository {
    override fun getEntrainements(): Flow<List<Entrainements>> {
        TODO("Not yet implemented")
    }

    override fun addEntrainements(e: Entrainements): Int {
        TODO("Not yet implemented")
    }

    override fun deleteEntrainements(id: Int): Int {
        TODO("Not yet implemented")
    }


}