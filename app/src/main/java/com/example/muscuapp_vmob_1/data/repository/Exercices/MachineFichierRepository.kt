package com.example.muscuapp_vmob_1.data.repository.Exercices

import android.content.Context
import com.example.muscuapp_vmob_1.model.Machine
import kotlinx.serialization.json.Json
class MachineFichierRepository(
    private val context: Context
) : MachinesRepository {

    override fun getMachine(): List<Machine> {
        val jsonString = context.assets
            .open("machines.json")
            .bufferedReader()
            .use { it.readText() }

        return Json.decodeFromString<List<Machine>>(jsonString)
    }

    override fun deleteMachine(id: Int): Int {
        TODO("Not yet implemented")
    }

    override fun addMachine(machine: Machine): Int {
        TODO("Not yet implemented")
    }
}