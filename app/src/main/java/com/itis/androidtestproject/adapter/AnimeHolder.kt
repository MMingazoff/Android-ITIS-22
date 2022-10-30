package com.itis.androidtestproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.androidtestproject.R
import com.itis.androidtestproject.databinding.ItemAnimeBinding
import com.itis.androidtestproject.model.BaseModel

class AnimeHolder(
    private val binding: ItemAnimeBinding,
    private val glide: RequestManager,
    private val action: (BaseModel.Anime) -> Unit,
    private val onDelete: (BaseModel.Anime, Int) -> Unit
): RecyclerView.ViewHolder(binding.root){

    fun onBind(anime: BaseModel.Anime) {
        binding.run {
            titleTv.text = anime.title
            authorTv.text = anime.author
            ratingTv.text = anime.rating.toString()
            glide
                .load(anime.poster)
                .centerCrop()
                .placeholder(R.drawable.progress_bar)
                .into(ivPoster)
            root.setOnClickListener{
                action(anime)
            }
            ivDelete.setOnClickListener {
                onDelete(anime, layoutPosition)
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            glide: RequestManager,
            action: (BaseModel.Anime) -> Unit,
            onDelete: (BaseModel.Anime, Int) -> Unit
        ) = AnimeHolder(
            ItemAnimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide,
            action,
            onDelete
        )
    }
}