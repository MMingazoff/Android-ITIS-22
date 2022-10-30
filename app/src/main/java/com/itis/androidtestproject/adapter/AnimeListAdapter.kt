package com.itis.androidtestproject.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.androidtestproject.R
import com.itis.androidtestproject.model.BaseModel
import com.itis.androidtestproject.model.Repository
import java.lang.IllegalStateException

class AnimeListAdapter(
    private val glide: RequestManager,
    private val action: (BaseModel.Anime) -> Unit
) : ListAdapter<BaseModel, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<BaseModel>() {
        override fun areItemsTheSame(
            oldItem: BaseModel,
            newItem: BaseModel
        ): Boolean =
            if (oldItem is BaseModel.Anime && newItem is BaseModel.Anime)
                oldItem.id == newItem.id
            else {
                if (oldItem is BaseModel.Ad && newItem is BaseModel.Ad)
                    oldItem.title == newItem.title
                else
                    false
            }

        override fun areContentsTheSame(
            oldItem: BaseModel,
            newItem: BaseModel
        ): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = when (viewType) {
        R.layout.item_anime -> AnimeHolder.create(parent, glide, action, ::onDeleteItem)
        R.layout.item_ad -> AdHolder.create(parent, glide)
        else -> throw IllegalStateException("Impossible view type")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is BaseModel.Anime -> (holder as AnimeHolder).onBind(item)
            is BaseModel.Ad -> (holder as AdHolder).onBind(item)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is BaseModel.Anime -> R.layout.item_anime
            is BaseModel.Ad -> R.layout.item_ad
        }

    fun onDeleteItem(anime: BaseModel.Anime, position: Int) {
        Repository.deleteAnime(anime.id)
//        notifyItemRangeChanged(position, 1)
        submitList(Repository.mainItems)
    }
}
