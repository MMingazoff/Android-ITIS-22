package com.itis.androidtestproject.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.itis.androidtestproject.R
import com.itis.androidtestproject.databinding.FragmentProfileBinding


class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private var binding: FragmentProfileBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding?.run {
            val navController = findNavController()
            val navOptions= NavOptions.Builder()
                .setLaunchSingleTop(false)
                .setEnterAnim(android.R.anim.slide_in_left)
                .setExitAnim(android.R.anim.slide_out_right)
                .setPopEnterAnim(android.R.anim.slide_in_left)
                .setPopExitAnim(android.R.anim.slide_out_right)
                .build()
            btnAbout.setOnClickListener {
                navController.navigate(R.id.action_profileFragment_to_aboutAppFragment, null, navOptions)
            }
            btnAccount.setOnClickListener {
                navController.navigate(R.id.action_profileFragment_to_accountInfoFragment, null, navOptions)
            }
            btnContact.setOnClickListener {
                navController.navigate(R.id.action_profileFragment_to_contactUsFragment, null, navOptions)
            }
            btnOrders.setOnClickListener {
                navController.navigate(R.id.action_profileFragment_to_ordersHistoryFragment, null, navOptions)
            }
            btnSettings.setOnClickListener {
                navController.navigate(R.id.action_profileFragment_to_settingsFragment, null, navOptions)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}