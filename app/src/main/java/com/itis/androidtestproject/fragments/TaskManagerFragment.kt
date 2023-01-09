package com.itis.androidtestproject.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.itis.androidtestproject.R
import com.itis.androidtestproject.data.Task
import com.itis.androidtestproject.data.TasksRepository
import com.itis.androidtestproject.databinding.FragmentTaskManagerBinding
import com.itis.androidtestproject.utils.prettify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TaskManagerFragment : Fragment(R.layout.fragment_task_manager) {
    private var binding: FragmentTaskManagerBinding? = null
    private var repository: TasksRepository? = null
    private var taskId: Int? = null
    private val calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTaskManagerBinding.bind(view)
        repository = TasksRepository(requireContext())
        arguments?.getInt(TASK_ID)?.let { id ->
            taskId = id
            lifecycleScope.launch {
                setEditTexts(id)
            }
        }
        if (taskId == null)
            setLocation()
        binding?.run {
            etDate.setOnClickListener {
                chooseDate()
            }
            btnSetCords.setOnClickListener {
                setLocation()
            }
            btnSave.setOnClickListener {
                saveTask()
            }
        }
    }

    private fun saveTask() {
        binding?.run {
            val newTitle = etTitle.text.toString()
            val newDescription = etDescription.text.toString()
            if (newTitle.isEmpty())
                etTitle.error = context?.getString(R.string.required_error)
            if (newDescription.isEmpty())
                etDescription.error = context?.getString(R.string.required_error)
            if (newTitle.isEmpty() || newDescription.isEmpty())
                return
            val longitude = etLongitude.text.toString().let {
                if (it.isNotEmpty())
                    it.toDouble()
                else
                    null
            }
            val latitude = etLatitude.text.toString().let {
                if (it.isNotEmpty())
                    it.toDouble()
                else
                    null
            }
            val date = if (etDate.text.isNotEmpty()) calendar.time else null
            lifecycleScope.launch(Dispatchers.IO) {
                if (taskId != null)
                    repository?.update(
                        Task(
                            id = taskId!!,
                            title = newTitle,
                            description = newDescription,
                            longitude = longitude,
                            latitude = latitude,
                            date = date
                        )
                    )
                else
                    repository?.add(
                        Task(
                            title = newTitle,
                            description = newDescription,
                            longitude = longitude,
                            latitude = latitude,
                            date = date
                        )
                    )
            }
            findNavController().navigateUp()
        }
    }

    @SuppressLint("MissingPermission")
    // проверка происходит в отдельной функции,
    // поэтому появляется ошибка, словно нет проверки на пермишены
    private fun setLocation() {
        if (!checkLocationPermissions()) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            activity?.requestPermissions(permissions, 0)
        }

        if (checkLocationPermissions()) {
            val locationManager =
                context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10000L,
                0f
            ) {
                binding?.run {
                    etLongitude.apply {
                        setText(it.longitude.toString())
                        isEnabled = true
                    }
                    etLatitude.apply {
                        setText(it.latitude.toString())
                        isEnabled = true
                    }
                }
            }
            Toast.makeText(
                context,
                context?.getString(R.string.info_toast),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            binding?.run {
                etLongitude.isEnabled = false
                etLatitude.isEnabled = false
            }
        }
    }

    private fun chooseDate() {
        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                calendar.apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                }
                binding?.etDate?.setText(calendar.time.prettify())
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun checkLocationPermissions(): Boolean =
        ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private suspend fun setEditTexts(taskId: Int) =
        withContext(Dispatchers.IO) {
            val task = repository?.get(taskId)
            withContext(Dispatchers.Main) {
                binding?.run {
                    etTitle.setText(task?.title)
                    etDescription.setText(task?.description)
                    etLongitude.setText(task?.longitude?.toString())
                    etLatitude.setText(task?.latitude?.toString())
                    etDate.setText(task?.date?.prettify())
                }
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        const val TASK_ID = "TASK_ID"
    }
}