package com.example.muscuapp_vmob_1.data.repository.Exercices

import com.example.muscuapp_vmob_1.model.Machine

interface MachinesRepository {
    fun getMachine(): List<Machine>
    fun deleteMachine(id: Int): Int
    fun addMachine(machine: Machine) : Int
}