package com.itis.androidtestproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import com.itis.androidtestproject.fragments.SongControlFragment
import com.itis.androidtestproject.fragments.SongsFragment
import com.itis.androidtestproject.model.SongsRepository

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null)
            return
        val songsFragment = SongsFragment()
        val song = intent?.getParcelableExtra<Song>(NotificationProvider.SONG)
        if (song != null)
            songsFragment.arguments =
                Bundle().apply {
                    putParcelable(NotificationProvider.SONG, song as Parcelable)
                }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, songsFragment)
            .commit()
    }
}
