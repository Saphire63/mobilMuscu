package com.muscuapp_vmob_1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.room.Room
import com.muscuapp_vmob_1.data.source.MuscuDataBase
import com.muscuapp_vmob_1.navigation.AppNavigation
import com.muscuapp_vmob_1.ui.views.theme.MuscuApp_VMob_1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            MuscuDataBase::class.java,
            MuscuDataBase.Companion.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDark = isSystemInDarkTheme()
            Log.d("THEME", "isDark = $isDark")
            MuscuApp_VMob_1Theme(darkTheme = true) {
                AppNavigation()
            }
        }
    }
}
