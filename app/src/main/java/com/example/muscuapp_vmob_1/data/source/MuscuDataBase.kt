package com.example.muscuapp_vmob_1.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.muscuapp_vmob_1.domain.model.MachineEntity

@Database( entities = [MachineEntity::class], version= 4 )
abstract  class MuscuDataBase : RoomDatabase(){
    abstract fun dao(): MachineDao
    companion object{
        const val DATABASE_NAME= "muscu.db"
    }
}