package com.itis.androidtestproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itis.androidtestproject.fragments.SongsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null)
            return
        val songsFragment = SongsFragment()
        val song = intent?.extras?.getParcelable<Song>(NotificationProvider.SONG)
        if (song != null)
            songsFragment.arguments = intent?.extras
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, songsFragment)
            .commit()
    }
}
