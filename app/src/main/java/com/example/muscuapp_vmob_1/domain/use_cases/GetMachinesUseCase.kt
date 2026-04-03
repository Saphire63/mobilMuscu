package com.example.muscuapp_vmob_1.domain.use_cases

import com.example.muscuapp_vmob_1.data.repository.exercices.MachineRepository
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.MachineVM
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMachinesUseCase @Inject constructor(
    private val repository: MachineRepository
) {
    operator fun invoke(): Flow<List<MachineVM>> {
        return repository.getMachines()
    }
}