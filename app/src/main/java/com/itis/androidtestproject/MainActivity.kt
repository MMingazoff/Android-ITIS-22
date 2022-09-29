package com.itis.androidtestproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.itis.androidtestproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding?.editButton?.setOnClickListener {
            binding?.fullName?.visibility =
                if (binding?.fullName?.visibility == View.GONE)
                    View.VISIBLE
                else
                    View.GONE
        }
    }
}
