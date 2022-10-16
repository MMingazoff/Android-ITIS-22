package com.itis.androidtestproject.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.androidtestproject.model.Anime
import com.itis.androidtestproject.databinding.ItemAnimeBinding

class AnimeHolder(
    private val binding: ItemAnimeBinding,
    private val glide: RequestManager,
    private val action: (Anime) -> Unit
): RecyclerView.ViewHolder(binding.root){
    fun onBind(anime: Anime) {
        binding.run {
            titleTv.text = anime.title
            authorTv.text = anime.author
            ratingTv.text = anime.rating.toString()
            glide
                .load(anime.poster)
                .into(posterIv)
            root.setOnClickListener{
                action(anime)
            }
        }
    }
}