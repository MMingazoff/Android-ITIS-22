package com.itis.androidtestproject.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.itis.androidtestproject.Constant
import com.itis.androidtestproject.R
import com.itis.androidtestproject.databinding.FragmentDialogBinding

class MyDialogFragment : DialogFragment(R.layout.fragment_dialog) {
    private lateinit var setNewCounter: (Int) -> Unit
    private var binding: FragmentDialogBinding? = null
    private var counter: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDialogBinding.bind(view)
        arguments?.getInt(Constant.COUNTER)?.let { counter = it }
        setClickListeners()
    }

    private fun setClickListeners() {
        binding?.run {
            positiveBtn.setOnClickListener{
                if (checkValue(mainEt.text.toString())) {
                    counter += mainEt.text.toString().toInt()
                    setNewCounter(counter)
                } else {
                    mainEt.error = resources.getString(R.string.error)
                }
            }
            neutralBtn.setOnClickListener{
                if (checkValue(mainEt.text.toString())) {
                    counter -= mainEt.text.toString().toInt()
                    if (counter < 0) counter = 0
                    setNewCounter(counter)
                } else {
                    mainEt.error = resources.getString(R.string.error)
                }
            }
            negativeBtn.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun checkValue(value: String?): Boolean {
        if (value == null) return false
        if (value.isBlank()) return false
        if (value.toIntOrNull() == null) return false
        if (value.toInt() in 1..100) return true
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        fun getInstance(counter: Int, setNewCounter: (Int) -> Unit) = MyDialogFragment().apply {
            arguments = Bundle().apply {
                putInt(Constant.COUNTER, counter)
            }
            this.setNewCounter = setNewCounter
        }
    }
}