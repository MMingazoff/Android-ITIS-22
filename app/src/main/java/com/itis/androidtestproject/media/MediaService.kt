package com.itis.androidtestproject.media

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.itis.androidtestproject.MediaAidlInterface
import com.itis.androidtestproject.NotificationProvider
import com.itis.androidtestproject.Song
import com.itis.androidtestproject.model.SongsRepository

class MediaService : Service() {
    private var mediaPlayer = MediaPlayer()
    private var notificationProvider: NotificationProvider? = null
    private val aidlBinder = object : MediaAidlInterface.Stub() {
        override fun start(song: Song?) {
            var newSong = false
            if (song == null)
                return
            if (song != SongsRepository.currentSong) {
                if (mediaPlayer.isPlaying)
                    mediaPlayer.stop()
                mediaPlayer = MediaPlayer.create(applicationContext, song.raw)
                SongsRepository.currentSong = song
                SongsRepository.currentSongTime = 0
                newSong = true
            }
            if (newSong) {
                mediaPlayer.run {
                    start()
                    setOnCompletionListener {
                        next()
                    }
                }
            } else {
                if (!mediaPlayer.isPlaying) {
                    play(SongsRepository.currentSongTime)
                } else
                    pause()
            }
            notificationProvider?.showMediaNotification(isPlaying, song)
        }

        override fun play(millis: Int) {
            if (mediaPlayer.isPlaying) mediaPlayer.stop()
            mediaPlayer.run {
                seekTo(millis)
                start()
                setOnCompletionListener {
                    next()
                }
            }
        }

        override fun pause() {
            mediaPlayer.pause()
            SongsRepository.currentSongTime = mediaPlayer.currentPosition + 1000
            // почему-то он минусует секудну, поэтому +1 секунда
        }

        override fun previous() {
            val index = SongsRepository.getCurrentSongIndex()
            val song =
                if (index == 0)
                    SongsRepository.songs.last()
                else
                    SongsRepository.songs[index - 1]
            start(song)
        }

        override fun next() {
            val index = SongsRepository.getCurrentSongIndex()
            val song =
                if (index == SongsRepository.songs.size - 1)
                    SongsRepository.songs.first()
                else
                    SongsRepository.songs[index + 1]
            start(song)
        }

        override fun stop() {
            mediaPlayer.pause()
            SongsRepository.currentSongTime = 0
        }

        override fun isPlaying(): Boolean = mediaPlayer.isPlaying

        override fun getCurrentSong(): Song = SongsRepository.currentSong

        override fun setCurrentSong(song: Song?) {
            song?.let {
                SongsRepository.currentSong = song
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        notificationProvider = NotificationProvider(baseContext)
        mediaPlayer = MediaPlayer.create(applicationContext, SongsRepository.currentSong.raw)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getParcelableExtra<MediaAction>(NotificationProvider.MEDIA_ACTION)?.let {
            with(aidlBinder) {
                when (it) {
                    MediaAction.PREV -> previous()
                    MediaAction.PLAY -> play(SongsRepository.currentSongTime)
                    MediaAction.PAUSE -> pause()
                    MediaAction.NEXT -> next()
                    MediaAction.STOP -> stop()
                }
                if (it == MediaAction.PLAY || it == MediaAction.PAUSE)
                    notificationProvider?.showMediaNotification(isPlaying, SongsRepository.currentSong)
                if (it == MediaAction.STOP)
                    notificationProvider?.deleteMediaNotification()
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = aidlBinder

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

}