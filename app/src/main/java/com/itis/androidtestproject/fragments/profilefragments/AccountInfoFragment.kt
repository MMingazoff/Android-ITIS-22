package com.itis.androidtestproject.fragments.profilefragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.itis.androidtestproject.R

class AccountInfoFragment: Fragment(R.layout.fragment_tempate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.let {
            it.findViewById<TextView>(R.id.tv_name).text = this::class.simpleName
            it.setBackgroundResource(R.color.orange)
        }
    }
}