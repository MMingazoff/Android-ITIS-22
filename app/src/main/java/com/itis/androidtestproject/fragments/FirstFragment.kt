package com.itis.androidtestproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.itis.androidtestproject.Constant
import com.itis.androidtestproject.R
import com.itis.androidtestproject.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    private var binding: FragmentFirstBinding? = null
    private var counter = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)
        arguments?.getInt(Constant.COUNTER)?.let {
            counter = it
            setCounter(counter)
        }
        setClickListeners()
    }

    private fun setCounter(counter: Int){
        binding?.counterTv?.text = Constant.getCounterString(counter)
    }

    private fun setClickListeners() {
        binding?.run {
            secondFragmentBtn.setOnClickListener{
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, SecondFragment.newInstance(counter))
                    .commit()
            }
            incrementBtn.setOnClickListener{
                counter++
                setCounter(counter)
            }
            dialogBtn.setOnClickListener{
                MyDialogFragment(counter) {
                    counter = it
                    setCounter(counter)
                }.show(parentFragmentManager, null)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(Constant.COUNTER, counter)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.getInt(Constant.COUNTER)?.also {
            counter = it
            setCounter(counter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    companion object {
        fun newInstance(counter: Int = 0) = FirstFragment().apply {
            arguments = Bundle().apply {
                putInt(Constant.COUNTER, counter)
            }
        }
    }
}