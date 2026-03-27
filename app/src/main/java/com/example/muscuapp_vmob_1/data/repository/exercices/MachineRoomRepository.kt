package com.example.muscuapp_vmob_1.data.repository.exercices

import com.example.muscuapp_vmob_1.data.source.MachineDao
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.MachineVM
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MachineRoomRepository @Inject constructor(
    private val dao: MachineDao
)
    : MachineRepository {
    override fun getMachine(): Flow<List<MachineVM>> {
        dao.getMachine()
    }

    override suspend fun deleteMachine(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun addMachine(machine: MachineVM) {
        TODO("Not yet implemented")
    }
}