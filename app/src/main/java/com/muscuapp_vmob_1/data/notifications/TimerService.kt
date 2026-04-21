package com.muscuapp_vmob_1.data.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import com.muscuapp_vmob_1.MainActivity

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TimerService : Service() {

    @Inject
    lateinit var timerManager: TimerManager

    companion object {
        const val CHANNEL_ID = "timer_channel"
        const val NOTIFICATION_ID = 101
        const val ACTION_STOP = "STOP_TIMER"
        const val EXTRA_TRAINING_NAME = "TRAINING_NAME"
        const val EXTRA_TRAINING_ID = "TRAINING_ID"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent?.action == ACTION_STOP) { // si l'intention est l'arrêt alors elle arrête la notif et dis plus rien ne tourne
            stopForeground(true)
            stopSelf()
            timerManager.stopTimer()
            return START_NOT_STICKY
        }

        val trainingName = intent?.getStringExtra(EXTRA_TRAINING_NAME) ?: "Entraînement"
        val trainingId = intent?.getIntExtra(EXTRA_TRAINING_ID, -1) ?: -1


        if (trainingId != -1) {
            timerManager.startTimer(trainingId)
        }
        createNotificationChannel()
        
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val stopIntent = Intent(this, TimerService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getService(
            this, 0, stopIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(trainingName)
            .setContentText("Entraînement en cours")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setUsesChronometer(true) // fonctionnalité native d'android pour afficher compteur sans consommer trop d'ernergie
            .setWhen(System.currentTimeMillis())
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .addAction(android.R.drawable.ic_media_pause, "Arrêter", stopPendingIntent)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() { // Créer un canal "Chrnonomètre d'entraînement"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Chronomètre d'entraînement",
                NotificationManager.IMPORTANCE_LOW // une petite importance pour éviter d'utiliser le song de la notif/ vibraions a chaques secondes
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}
