package com.itis.androidtestproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.androidtestproject.model.Anime
import com.itis.androidtestproject.databinding.ItemAnimeBinding

class AnimeAdapter(
    private val list: List<Anime>,
    private val glide: RequestManager,
    private val action: (Anime) -> Unit
): RecyclerView.Adapter<AnimeHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AnimeHolder(
        ItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        glide,
        action
    )

    override fun onBindViewHolder(holder: AnimeHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}