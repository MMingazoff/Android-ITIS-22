package com.itis.androidtestproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.itis.androidtestproject.MediaAidlInterface
import com.itis.androidtestproject.R
import com.itis.androidtestproject.Song
import com.itis.androidtestproject.databinding.FragmentSongControlBinding
import com.itis.androidtestproject.model.SongsRepository

class SongControlFragment(
    private val getBinder: () -> MediaAidlInterface?
): Fragment(R.layout.fragment_song_control) {
    private var binding: FragmentSongControlBinding? = null
    private var song = getBinder()?.currentSong!!
    private val songs = SongsRepository.songs
    private var songIndex = SongsRepository.getSongIndex(song)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSongControlBinding.inflate(layoutInflater)
        setUI()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding?.run {
            btnPrevious.setOnClickListener {
                getBinder()?.previous()
                if (songIndex == 0) {
                    song = songs.last()
                    songIndex = songs.size - 1
                } else {
                    songIndex -= 1
                    song = songs[songIndex]
                }
                setUI()
            }
            btnPlay.setOnClickListener {
                getBinder()?.start(song)
                setPlayButton()
            }
            btnNext.setOnClickListener {
                getBinder()?.next()
                if (songIndex == songs.size - 1) {
                    song = songs.first()
                    songIndex = 0
                } else {
                    songIndex += 1
                    song = songs[songIndex]
                }
                setUI()
            }
            btnStop.setOnClickListener {
                getBinder()?.stop()
                setPlayButton()
            }
        }
    }

    private fun setUI() {
        binding?.run {
            ivCoverFragment.setImageResource(song.cover)
            tvArtistFragment.text = song.artist
            tvTitleFragment.text = song.title
        }
        setPlayButton()
    }

    private fun setPlayButton() {
        binding?.run {
            getBinder()?.let {
                if (it.isPlaying)
                    btnPlay.setImageResource(R.drawable.ic_pause_big)
                else
                    btnPlay.setImageResource(R.drawable.ic_play_big)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setPlayButton()
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}