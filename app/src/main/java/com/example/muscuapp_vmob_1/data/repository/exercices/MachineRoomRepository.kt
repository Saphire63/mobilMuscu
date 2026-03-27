package com.example.muscuapp_vmob_1.data.repository.exercices

import com.example.muscuapp_vmob_1.data.source.MachineDao
import com.example.muscuapp_vmob_1.domain.model.MachineEntity.Companion.toVM
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.MachineVM
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.MachineVM.Companion.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MachineRoomRepository @Inject constructor(
    private val dao: MachineDao
)
    : MachineRepository {
    override fun getMachines(): Flow<List<MachineVM>> {
        return dao.getMachines().map { entities ->
            entities.map { it.toVM() }
        }
    }

    override suspend fun getMachine(id: Int): MachineVM? {
        return dao.getMachine(id)?.toVM()
    }

    override suspend fun upsertMachine(machine: MachineVM) {
        dao.upsertMachine(machine.toEntity())
    }

    override suspend fun deleteMachine(machine: MachineVM) {
        dao.deleteMachine(machine.toEntity())
    }

}