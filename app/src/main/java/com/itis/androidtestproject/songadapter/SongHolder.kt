package com.itis.androidtestproject.songadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidtestproject.MediaAidlInterface
import com.itis.androidtestproject.databinding.SongItemBinding
import com.itis.androidtestproject.Song

class SongHolder(
    private val binding: SongItemBinding,
    private val getBinder: () -> MediaAidlInterface?,
    private val setBackground: (View, Song) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    fun onBind(song: Song) {
        binding.run {
            ivCover.setImageResource(song.cover)
            tvArtist.text = song.artist
            tvTitle.text = song.title
            root.setOnClickListener {
                getBinder()?.start(song)
                setBackground(binding.root, song)
            }
        }
    }
}