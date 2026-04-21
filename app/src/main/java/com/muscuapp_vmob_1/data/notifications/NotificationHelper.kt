import android.annotation.SuppressLint // N'oublie pas cet import
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.muscuapp_vmob_1.data.notifications.AlarmReceiver
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.*

// Cette ligne dit à Android Studio d'ignorer l'alerte de permission pour cette fonction
@SuppressLint("MissingPermission")
fun showMyNotification(context: Context) {

    val notification = NotificationCompat.Builder(context, "channel_id")
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Hello!")
        .setContentText("Voici une belle notification.")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)
        .build()

    // Le trait rouge va disparaître ici !
    NotificationManagerCompat.from(context).notify(1, notification)
}



fun scheduleNotification(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // On prépare l'intention : "Quand l'alarme sonne, ouvre AlarmReceiver"
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    // Définir l'heure à 19h05
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 19)
        set(Calendar.MINUTE, 5)
        set(Calendar.SECOND, 0)

        // Si 19h05 est déjà passé aujourd'hui, on programme pour demain
        if (before(Calendar.getInstance())) {
            add(Calendar.DATE, 1)
        }
    }

    // Planifier l'alarme
    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}