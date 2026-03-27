package com.example.muscuapp_vmob_1.data.repository.exercices

import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.MachineVM
import kotlinx.coroutines.flow.Flow

interface MachineRepository {

    fun getMachines(): Flow<List<MachineVM>>

    suspend  fun getMachine(id: Int): MachineVM

    suspend fun upsertMachine(machine: MachineVM )

    suspend fun deleteMachine(id: Int)
}