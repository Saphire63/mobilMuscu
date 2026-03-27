package com.example.muscuapp_vmob_1.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.muscuapp_vmob_1.domain.model.MachineEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

@Dao
interface MachineDao {

    @Query ("SELECT * FROM machines")
    fun getMachines(): Flow<List<MachineEntity>>

    @Query("SELECT * FROM machines WHERE ID = :id")
    suspend fun getMachine(id: Int): MachineEntity?
    @Delete
    suspend fun deleteMachine(machine: MachineEntity)

    @Upsert
    suspend fun upsertMachine(machine: MachineEntity )


}