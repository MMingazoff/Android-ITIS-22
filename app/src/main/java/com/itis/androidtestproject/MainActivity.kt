package com.itis.androidtestproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itis.androidtestproject.fragments.GeneralFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null)
            return
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, GeneralFragment())
            .commit()
    }
}
