package com.itis.androidtestproject.songadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidtestproject.MediaAidlInterface
import com.itis.androidtestproject.databinding.SongItemBinding
import com.itis.androidtestproject.Song
import com.itis.androidtestproject.utils.Click

class SongAdapter(
    private val songs: List<Song>,
    private val getBinder: () -> MediaAidlInterface?,
    private val setBackground: (View, Song) -> Unit
): RecyclerView.Adapter<SongHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SongHolder(
        SongItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        getBinder,
        setBackground
    )

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        holder.onBind(songs[position])
    }

    override fun getItemCount(): Int {
        return songs.size
    }
}