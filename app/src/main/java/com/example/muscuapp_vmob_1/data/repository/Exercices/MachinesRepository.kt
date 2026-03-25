package com.example.muscuapp_vmob_1.data.repository.Exercices

import com.example.muscuapp_vmob_1.model.Machine
import kotlinx.coroutines.flow.Flow

interface MachinesRepository {
    fun getMachine(): Flow<List<Machine>>
    fun deleteMachine(id: Int): Int
    fun addMachine(machine: Machine) : Int
}