package com.itis.androidtestproject.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.itis.androidtestproject.Constant
import com.itis.androidtestproject.R
import com.itis.androidtestproject.databinding.FragmentSecondBinding

class SecondFragment: Fragment(R.layout.fragment_second) {

    private var binding: FragmentSecondBinding? = null
    private var counter: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)
        arguments?.let{
            counter = it.getInt(Constant.COUNTER)
        }
        binding?.run {
            constraint.setBackgroundResource(when (counter) {
                in 0..50 -> R.color.green
                in 51..100 -> R.color.orange
                else -> R.color.pink
            })
            counterTv.text = Constant.getCounterString(counter)
        }
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, FirstFragment.newInstance(counter))
                        .commit()
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        fun newInstance(counter: Int = 0) = SecondFragment().apply {
            arguments = Bundle().apply {
                putInt(Constant.COUNTER, counter)
            }
        }
    }
}