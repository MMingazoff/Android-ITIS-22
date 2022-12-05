package com.itis.androidtestproject.fragments

import android.content.ComponentName
import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.itis.androidtestproject.*
import com.itis.androidtestproject.databinding.FragmentSongsBinding
import com.itis.androidtestproject.media.MediaService
import com.itis.androidtestproject.model.SongsRepository
import com.itis.androidtestproject.songadapter.ItemSpacerDecoration
import com.itis.androidtestproject.songadapter.SongAdapter

class SongsFragment : Fragment(R.layout.fragment_songs) {
    private var binding: FragmentSongsBinding? = null
    private var binder: MediaAidlInterface? = null
    private var playingSong = SongsRepository.currentSong
    private var playingSongView: View? = null
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = MediaAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
        }
    }

    override fun onStart() {
        super.onStart()
        activity?.bindService(
            Intent(context, MediaService::class.java),
            connection,
            Service.BIND_AUTO_CREATE
        )
        arguments?.getParcelable<Song>(NotificationProvider.SONG)?.let {
            binder?.currentSong = it
            val isPlaying = arguments?.getBoolean(NotificationProvider.IS_PLAYING)
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                .replace(R.id.container, SongControlFragment(it, isPlaying) { binder })
                .addToBackStack(null)
                .commit()
        }
        arguments = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentSongsBinding.inflate(layoutInflater)
        binding?.run {
            rvSongs.addItemDecoration(
                ItemSpacerDecoration(
                    requireContext(),
                    8f
                )
            )
            rvSongs.adapter = SongAdapter(
                SongsRepository.songs,
                { binder },
                { view, song -> setPlaying(view, song) }
            )
            btnSongDetails.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right
                    )
                    .replace(
                        R.id.container,
                        SongControlFragment(binder?.currentSong!!, null) { binder })
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun setPlaying(view: View, song: Song) {
        if (song != playingSong) {
            playingSongView?.setBackgroundResource(R.color.white)
            playingSongView = view
            playingSong = song
        }
        binder?.let {
            if (it.isPlaying)
                view.setBackgroundResource(R.color.grey_active)
            else
                view.setBackgroundResource(R.color.grey_paused)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = binding?.root

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        activity?.unbindService(connection)
    }
}