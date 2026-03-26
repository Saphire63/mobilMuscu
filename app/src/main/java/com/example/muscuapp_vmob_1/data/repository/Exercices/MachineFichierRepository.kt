package com.example.muscuapp_vmob_1.data.repository.Exercices

import android.content.Context
import com.example.muscuapp_vmob_1.model.Machine
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MachineFichierRepository @Inject constructor (
    @ApplicationContext private val context: Context
) : MachinesRepository {

    override fun getMachine(): Flow<List<Machine>> = flow {
        val jsonString = context.assets
            .open("machines.json")
            .bufferedReader()
            .use { it.readText() }

        val machines = Json.decodeFromString<List<Machine>>(jsonString)

        emit(machines)
    }


    override fun deleteMachine(id: Int): Int {
        TODO("Not yet implemented")
    }

    override fun addMachine(machine: Machine): Int {
        TODO("Not yet implemented") //edit a voir sur diapo 69
    }
}