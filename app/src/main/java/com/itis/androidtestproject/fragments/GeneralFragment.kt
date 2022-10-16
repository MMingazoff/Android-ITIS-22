package com.itis.androidtestproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.itis.androidtestproject.adapter.AnimeAdapter
import com.itis.androidtestproject.model.AnimeRepository
import com.itis.androidtestproject.R
import com.itis.androidtestproject.adapter.SpaceItemDecoration
import com.itis.androidtestproject.databinding.FragmentGeneralBinding
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter

class GeneralFragment: Fragment(R.layout.fragment_general) {
    private var binding: FragmentGeneralBinding? = null
    private var adapter: AnimeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentGeneralBinding.inflate(layoutInflater)
        binding?.run {
            val itemDecoration = SpaceItemDecoration(requireContext(), 16f)
            adapter = AnimeAdapter(
                AnimeRepository.anime,
                Glide.with(this@GeneralFragment)
            ) {
                parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right
                    )
                    .replace(R.id.container, DescriptionFragment(it.id))
                    .addToBackStack(null)
                    .commit()
            }
            mainRv.addItemDecoration(itemDecoration)
            mainRv.adapter =  AlphaInAnimationAdapter(adapter!!).apply {
                setDuration(500)
                setFirstOnly(false)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}