package com.example.muscuapp_vmob_1.data.repository.exercices

import android.content.Context
import com.example.muscuapp_vmob_1.domain.model.MachineEntity
import com.example.muscuapp_vmob_1.domain.model.MachineEntity.Companion.toVM
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.MachineVM
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MachineFichierRepository @Inject constructor (
    @ApplicationContext private val context: Context
) : MachineRepository {

    override fun getMachines(): Flow<List<MachineVM>> = flow {
        val jsonString = context.assets
            .open("machines.json")
            .bufferedReader()
            .use { it.readText() }

        val machines = Json.decodeFromString<List<MachineEntity>>(jsonString)
            .map{ it.toVM() }

        emit(machines)
    }

    override suspend fun getMachine(id: Int): MachineVM? {
        TODO("Not yet implemented")
    }

    override suspend fun upsertMachine(machine: MachineVM) {
        TODO("Not yet implemented")
    }


    override suspend fun deleteMachine(machine: MachineVM) {
        TODO("Not yet implemented")
    }



}