package com.itis.androidtestproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.itis.androidtestproject.databinding.ActivitySecondBinding


class SecondActivity : AppCompatActivity(){
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding.photo.setImageURI(intent.getParcelableExtra(Intent.EXTRA_STREAM))
    }
}
