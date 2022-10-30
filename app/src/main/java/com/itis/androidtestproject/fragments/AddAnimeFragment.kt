package com.itis.androidtestproject.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.itis.androidtestproject.R
import com.itis.androidtestproject.databinding.FragmentAddAnimeBinding
import com.itis.androidtestproject.model.Repository

class AddAnimeFragment(private val onAdd: (Int) -> Unit): DialogFragment(R.layout.fragment_add_anime) {
    private var binding: FragmentAddAnimeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddAnimeBinding.bind(view)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding?.run {
            btnAdd.setOnClickListener {
                val position = getPosition()
                if (setErrors()) {
                    Repository.addAnime(
                        title = etTitle.text.toString(),
                        author = etAuthor.text.toString(),
                        description = etDescription.text.toString(),
                        poster = etPoster.text.toString(),
                        position = position
                    )
                    onAdd(position)
                    dismiss()
                }
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun getPosition(): Int {
        binding?.run {
            val positionValue = etPosition.text
            if (positionValue.isNullOrBlank())
                return Repository.getAnimeSize()
            if (positionValue.toString().toInt() >= Repository.getAnimeSize())
                return Repository.getAnimeSize()
            if (positionValue.toString().toInt() == 0)
                return 0
            return positionValue.toString().toInt() - 1
        }
        return 0
    }

    private fun setErrors(): Boolean {
        var flag = true
        binding?.run {
            resources.getString(R.string.error).also {
                if (etTitle.text.isNullOrBlank()) {
                    etTitle.error = it
                    flag = false
                }
                if (etAuthor.text.isNullOrBlank()) {
                    etAuthor.error = it
                    flag = false
                }
                if (etDescription.text.isNullOrBlank()) {
                    etDescription.error = it
                    flag = false
                }
                if (etPoster.text.isNullOrBlank()) {
                    etPoster.error = it
                    flag = false
                }
            }
        }
        return flag
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}