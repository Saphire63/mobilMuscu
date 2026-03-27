package com.example.muscuapp_vmob_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.muscuapp_vmob_1.data.source.MuscuDataBase
import com.example.muscuapp_vmob_1.navigation.AppNavigation
import com.example.muscuapp_vmob_1.ui.views.theme.MuscuApp_VMob_1Theme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            MuscuDataBase::class.java,
            MuscuDataBase.DATABASE_NAME
        ).build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MuscuApp_VMob_1Theme {
                AppNavigation()
            }
        }
    }
}
