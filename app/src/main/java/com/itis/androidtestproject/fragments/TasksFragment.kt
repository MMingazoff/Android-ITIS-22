package com.itis.androidtestproject.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.itis.androidtestproject.R
import com.itis.androidtestproject.data.TasksRepository
import com.itis.androidtestproject.databinding.FragmentTasksBinding
import com.itis.androidtestproject.recyclerview.TaskListAdapter
import com.itis.androidtestproject.utils.LayoutType
import com.itis.androidtestproject.utils.SpaceItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TasksFragment : Fragment(R.layout.fragment_tasks) {
    private var binding: FragmentTasksBinding? = null
    private var repository: TasksRepository? = null
    private var adapter: TaskListAdapter? = null
    private val menuHost: MenuHost
        get() = requireActivity()
    private var nightMode = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTasksBinding.bind(view)
        repository = TasksRepository(requireContext())
        adapter = TaskListAdapter({
            lifecycleScope.launch(Dispatchers.IO) {
                repository?.delete(it)
                fillRvTasks()
            }
        }) {
            findNavController().navigate(
                R.id.action_tasksFragment_to_taskManagerFragment,
                Bundle().apply {
                    putInt(TaskManagerFragment.TASK_ID, it)
                }
            )
        }.also {
            binding?.rvTasks?.adapter = it
            binding?.rvTasks?.addItemDecoration(
                SpaceItemDecoration(requireContext(), 16f)
            )
        }
        binding?.fabAdd?.setOnClickListener {
            findNavController().navigate(R.id.action_tasksFragment_to_taskManagerFragment)
        }
        setLayoutManager()
        setTheme()
        lifecycleScope.launch { fillRvTasks() }
        setMenuItems()
    }

    private fun setTheme() {
        val pref = activity?.getPreferences(Context.MODE_PRIVATE)
        pref?.getBoolean(NIGHT_MODE, false)?.let {
            nightMode = it
            AppCompatDelegate.setDefaultNightMode(
                if (it)
                    AppCompatDelegate.MODE_NIGHT_YES
                else
                    AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }

    private fun setMenuItems() {
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
                if (adapter?.layoutType == LayoutType.GRID)
                    menu[1].setIcon(R.drawable.ic_linear)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.change_layout -> {
                        val pref = activity?.getPreferences(Context.MODE_PRIVATE)
                        binding?.run {
                            when (adapter?.layoutType) {
                                LayoutType.LINEAR -> {
                                    adapter?.layoutType = LayoutType.GRID
                                    rvTasks.layoutManager = GridLayoutManager(context, 2)
                                    menuItem.setIcon(R.drawable.ic_linear)
                                    pref?.edit()?.run {
                                        putBoolean(LAYOUT_MODE, true)
                                        commit()
                                    }
                                }
                                LayoutType.GRID -> {
                                    adapter?.layoutType = LayoutType.LINEAR
                                    rvTasks.layoutManager = LinearLayoutManager(context)
                                    menuItem.setIcon(R.drawable.ic_grid)
                                    pref?.edit()?.run {
                                        putBoolean(LAYOUT_MODE, false)
                                        commit()
                                    }
                                }
                                null -> throw java.lang.IllegalStateException()
                            }
                            // это не создание нового адаптера,
                            // просто при переприсваивании обновляются вьюхолдеры
                            rvTasks.adapter = adapter
                        }
                    }
                    R.id.delete_all -> {
                        lifecycleScope.launch(Dispatchers.IO) {
                            repository?.deleteAll()
                        }
                        adapter?.submitList(emptyList())
                        binding?.tvStartMsg?.visibility = View.VISIBLE
                    }
                    R.id.change_theme -> {
                        nightMode = !nightMode
                        val pref = activity?.getPreferences(Context.MODE_PRIVATE)
                        if (nightMode) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        }
                        pref?.edit()?.run {
                            putBoolean(NIGHT_MODE, nightMode)
                            commit()
                        }
                    }
                }
                return true
            }
        }, viewLifecycleOwner)
    }

    private fun setLayoutManager() {
        val pref = activity?.getPreferences(Context.MODE_PRIVATE)
        pref?.getBoolean(LAYOUT_MODE, false)?.let {
            binding?.rvTasks?.layoutManager =
                if (it)
                    GridLayoutManager(context, 2).also {
                        adapter?.layoutType = LayoutType.GRID
                    }
                else
                    LinearLayoutManager(context)
        }
    }

    private suspend fun fillRvTasks() = withContext(Dispatchers.IO) {
        val tasks = repository?.getAll()
        withContext(Dispatchers.Main) {
            adapter?.submitList(tasks)
            binding?.tvStartMsg?.visibility =
                if (tasks?.isEmpty() == true)
                    View.VISIBLE
                else
                    View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        private const val LAYOUT_MODE = "LAYOUT_MODE"
        const val NIGHT_MODE = "NIGHT_MODE"
    }
}