package com.itis.androidtestproject

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.widget.Toast
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    fun setAlarm(calendar: Calendar, context: Context, alarmManager: AlarmManager) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(setName, true)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            200,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
        val toastText = context.getString(
            R.string.toast_set,
            SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.ENGLISH)
                .format(calendar.time)
        )
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context, alarmManager: AlarmManager) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(setName, false)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            200,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
        val toastText = context.getString(R.string.toast_cancel)
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
    }

    override fun onReceive(context: Context, intent: Intent) {
        /*if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val sharedPref = context.getSharedPreferences(
                context.getString(R.string.pref_name),
                Context.MODE_PRIVATE
            )
            sharedPref?.run {
                val timeInMillis = getLong(context.getString(R.string.pref_key), 0)
                if (timeInMillis != 0L) {
                    val alarmManager =
                        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    val calendar = Calendar.getInstance().apply {
                        this.timeInMillis = timeInMillis
                    }
                    setAlarm(calendar, context, alarmManager)
                }
                return
            }
        }*/
        intent.extras?.getBoolean(setName)?.also {
            if (it) {
                val pendingIntent = PendingIntent.getActivity(
                    context,
                    200,
                    Intent(context, SecondActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
                NotificationProvider(context).showNotification(pendingIntent)
            }
        }
    }

    companion object {
        private const val setName = "set"
    }
}