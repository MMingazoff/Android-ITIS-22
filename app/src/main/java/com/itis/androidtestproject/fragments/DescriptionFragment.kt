package com.itis.androidtestproject.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.itis.androidtestproject.R
import com.itis.androidtestproject.databinding.FragmentDescriptionBinding
import com.itis.androidtestproject.model.Repository

class DescriptionFragment(private val anime_id: Int): Fragment(R.layout.fragment_description) {
    private var binding: FragmentDescriptionBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDescriptionBinding.bind(view)
        binding?.run {
            val anime = Repository.getAnime(anime_id)
            toolbar.title = anime?.title
            ratingTv.text = anime?.rating.toString()
            descriptionTv.text = anime?.description
            Glide.with(this@DescriptionFragment)
                .load(anime?.poster)
                .into(ivPoster)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}