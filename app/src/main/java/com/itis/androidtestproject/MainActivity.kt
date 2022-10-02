package com.itis.androidtestproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.itis.androidtestproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        binding.openLink.setOnClickListener {
            shareLink()
        }
        binding.sharePhoto.setOnClickListener {
            shareCatPhoto()
        }
        binding.choosePic.setOnClickListener {
            getPhotoFromGallery()
        }
    }

    private fun shareLink() {
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse("https://youtu.be/dQw4w9WgXcQ")
        }
        val title = resources.getString(R.string.share_link)
        val chooser = Intent.createChooser(intent, title)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        }
    }

    private fun shareCatPhoto() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, Uri.parse("android.resource://com.itis.androidtestproject/" + R.drawable.cat))
        }
        val title = resources.getString(R.string.share_cat)
        val chooser = Intent.createChooser(intent, title)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        }
    }

    // нашел в интернете, так как startActivityForResult(intent) считается устаревшим способом, а я за новую школу :)
    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val uri = it?.data?.data
            binding.chosenPic.setImageURI(uri)
        }
    }

    private fun getPhotoFromGallery() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        if (intent.resolveActivity(packageManager) != null) {
            getResult.launch(intent)
        }
    }
}
