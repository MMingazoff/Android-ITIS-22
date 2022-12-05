package com.itis.androidtestproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Parcelable
import androidx.core.app.NotificationCompat
import com.itis.androidtestproject.media.MediaAction
import com.itis.androidtestproject.media.MediaService

class NotificationProvider(private val context: Context) {
    fun showMediaNotification(isPlaying: Boolean, song: Song) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent = PendingIntent.getActivity(
            context,
            6,
            Intent(context, MainActivity::class.java).apply {
                putExtra(SONG, song as Parcelable)
                putExtra(IS_PLAYING, isPlaying)
            },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val style = androidx.media.app.NotificationCompat.MediaStyle()
            .setShowActionsInCompactView()
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(
                context,
                context.getString(R.string.notification_channel_id)
            )
                .setSmallIcon(R.drawable.ic_note)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources,
                        song.cover
                    )
                )
                .setContentTitle(song.title)
                .setContentText(song.artist)
                .addAction(R.drawable.ic_previous, "Previous", getPendingIntent(MediaAction.PREV))
                .addAction(
                    if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play,
                    if (isPlaying) "Pause" else "Play",
                    if (isPlaying) getPendingIntent(MediaAction.PAUSE) else getPendingIntent(MediaAction.PLAY)
                )
                .addAction(R.drawable.ic_next, "Next", getPendingIntent(MediaAction.NEXT))
                .addAction(R.drawable.ic_stop, "Stop", getPendingIntent(MediaAction.STOP))
                .setContentIntent(pendingIntent)
                .setStyle(style)
                .setOngoing(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                context.getString(R.string.notification_channel_id),
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_LOW
            ).also {
                notificationManager.createNotificationChannel(it)
            }
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    fun deleteMediaNotification() =
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(NOTIFICATION_ID)

    private fun getPendingIntent(action: MediaAction): PendingIntent {
        val requestCode = when (action) {
            MediaAction.PREV -> 1
            MediaAction.PLAY -> 2
            MediaAction.PAUSE -> 3
            MediaAction.NEXT -> 4
            MediaAction.STOP -> 5
        }
        val intent = Intent(
            context,
            MediaService::class.java
        ).apply {
            putExtra(MEDIA_ACTION, action as Parcelable)
        }
        return PendingIntent.getService(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        const val MEDIA_ACTION = "MEDIA_ACTION"
        const val SONG = "SONG"
        const val IS_PLAYING = "IS_PLAYING"
        const val NOTIFICATION_ID = 1337
    }
}