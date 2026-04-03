package com.example.muscuapp_vmob_1.domain.use_cases

import com.example.muscuapp_vmob_1.data.repository.exercices.MachineRepository
import com.example.muscuapp_vmob_1.ui.viewmodel.objectsVm.machines.MachineVM
import javax.inject.Inject

class GetMachineUseCase @Inject constructor(
    private val repository: MachineRepository
) {
    suspend operator fun invoke(id: Int): MachineVM? {
        return repository.getMachine(id)
    }
}