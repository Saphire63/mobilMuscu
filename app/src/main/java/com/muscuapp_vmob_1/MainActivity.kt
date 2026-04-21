package com.muscuapp_vmob_1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
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

    //ici je me suis documenté sur la doc Android puis j'ai utilisé les fonctionnalités de l'IDE pour compléter
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val channel = NotificationChannel(
            "channel_id",
            "Time to Train",
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            description = "It's 12p.m. - Time to train !!!"
        }
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
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
