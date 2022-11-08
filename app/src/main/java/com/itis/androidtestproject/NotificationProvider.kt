package com.itis.androidtestproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat


class NotificationProvider(private val context: Context) {
    fun showNotification(pendingIntent: PendingIntent) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val vibrations = longArrayOf(500, 1000, 500, 1000)
        val audioAttributes: AudioAttributes =
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
        val sound: Uri = Uri.parse(
            "android.resource://" + context.packageName + "/" + R.raw.gachi_song
        )
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(
                context,
                context.getString(R.string.notification_channel_id)
            )
                .setSmallIcon(R.drawable.ic_gachi)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.ic_gachi_large
                    )
                )
                .setContentTitle(context.getString(R.string.title_notification))
                .setContentText(context.getString(R.string.text_notification))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                context.getString(R.string.notification_channel_id),
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor = Color.BLUE
                vibrationPattern = vibrations
                setSound(sound, audioAttributes)
            }.also {
                notificationManager.createNotificationChannel(it)
            }
        } else {
            builder
                .setVibrate(vibrations)
                .setSound(sound)
                .setLights(Color.BLUE, 100, 200)
        }
        notificationManager.notify(1337, builder.build())
    }
}