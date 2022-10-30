package com.itis.androidtestproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.itis.androidtestproject.R
import com.itis.androidtestproject.adapter.AnimeHolder
import com.itis.androidtestproject.adapter.AnimeListAdapter
import com.itis.androidtestproject.adapter.SpaceItemDecoration
import com.itis.androidtestproject.databinding.FragmentGeneralBinding
import com.itis.androidtestproject.model.BaseModel
import com.itis.androidtestproject.model.Repository
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter

class GeneralFragment: Fragment(R.layout.fragment_general) {
    private var binding: FragmentGeneralBinding? = null
    private var listAdapter: AnimeListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentGeneralBinding.inflate(layoutInflater)
        binding?.run {
            val itemDecoration = SpaceItemDecoration(requireContext(), 16f)
            listAdapter = AnimeListAdapter(
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
            }.apply {
                submitList(Repository.mainItems)
                createItemTouchHelper(this).attachToRecyclerView(mainRv)
                fab.setOnClickListener {
                    AddAnimeFragment {
//                        notifyItemRangeChanged(it + 1, 1)
                        submitList(Repository.mainItems)
                    }.show(parentFragmentManager, null)
                }
            }
            mainRv.addItemDecoration(itemDecoration)
            mainRv.adapter = AlphaInAnimationAdapter(listAdapter!!).apply {
                setDuration(500)
                setFirstOnly(false)
            }
        }
    }

    private fun createItemTouchHelper(adapter: AnimeListAdapter) : ItemTouchHelper {
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(
                    0,
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                ) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean = false

                    override fun getSwipeDirs(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder
                    ): Int {
                        if (viewHolder !is AnimeHolder)
                            return ItemTouchHelper.ACTION_STATE_IDLE
                        return super.getSwipeDirs(recyclerView, viewHolder)
                    }

                    override fun onSwiped(
                        viewHolder: RecyclerView.ViewHolder,
                        direction: Int
                    ) {
                        Toast.makeText(
                            context,
                            getString(R.string.on_deleted),
                            Toast.LENGTH_SHORT
                        ).show()
                        adapter.onDeleteItem(
                            Repository.mainItems[viewHolder.layoutPosition] as BaseModel.Anime,
                            viewHolder.layoutPosition
                        )
                    }
                }
        return ItemTouchHelper(itemTouchHelperCallback)
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