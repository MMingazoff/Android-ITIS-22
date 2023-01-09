package com.itis.androidtestproject

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.itis.androidtestproject.fragments.FirstFragment
import com.itis.androidtestproject.fragments.SecondFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null)
            return
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FirstFragment.newInstance())
            .commit()
    }
}
