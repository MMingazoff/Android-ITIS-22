package com.itis.androidtestproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.androidtestproject.databinding.ItemAdBinding
import com.itis.androidtestproject.model.BaseModel

class AdHolder(
    private val binding: ItemAdBinding,
    private val glide: RequestManager
): RecyclerView.ViewHolder(binding.root) {
    fun onBind(ad: BaseModel.Ad) {
        binding.run {
            tvTitle.text = ad.title
            tvDescription.text = ad.description
            glide
                .load(ad.photo)
                .centerCrop()
                .into(ivPhoto)
        }
    }

    companion object {
        fun create(parent: ViewGroup, glide: RequestManager) = AdHolder(
            ItemAdBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide
        )
    }
}