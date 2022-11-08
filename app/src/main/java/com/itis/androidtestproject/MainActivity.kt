package com.itis.androidtestproject

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.itis.androidtestproject.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var alarmManager: AlarmManager
    private var binding: ActivityMainBinding? = null
    private val calendar = Calendar.getInstance()
    private var isTimeSet = false
    private var isDateSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
        alarmReceiver = AlarmReceiver()
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
//        val sharedPref = getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
        binding?.run {
            tvDate.setOnClickListener {
                chooseDate(tvDate)
            }
            tvTime.setOnClickListener {
                chooseTime(tvTime)
            }
            btnStart.setOnClickListener {
                if (isDateSet && isTimeSet) {
                    alarmReceiver.setAlarm(calendar, this@MainActivity, alarmManager)
                    /*with (sharedPref.edit()) {
                        putLong(getString(R.string.pref_key), calendar.timeInMillis)
                        apply()
                    }*/
                } else {
                    val errorMessage =
                        if (isDateSet)
                            getString(R.string.time_error)
                        else
                            getString(R.string.date_error)
                    Toast.makeText(
                        this@MainActivity,
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            btnStop.setOnClickListener {
                if (isDateSet && isTimeSet) {
                    alarmReceiver.cancelAlarm(this@MainActivity, alarmManager)
                    /*with (sharedPref.edit()) {
                        putInt(getString(R.string.pref_key), 0)
                        apply()
                    }*/
                }
            }
        }
    }

    private fun chooseDate(textView: TextView) {
        DatePickerDialog(
            this,
            { _, year, month, day ->
                calendar.apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                }
                textView.text =
                    SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
                        .format(calendar.time)
                isDateSet = true
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun chooseTime(textView: TextView) {
        TimePickerDialog(
            this,
            { _, hour, minute ->
                calendar.apply {
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                }
                textView.text =
                    SimpleDateFormat("HH:mm", Locale.ENGLISH)
                        .format(calendar.time)
                isTimeSet = true
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
