package com.dailystudio.navigation.animation.fragment

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.dailystudio.navigation.animation.R


class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as AppCompatActivity)
            .supportActionBar?.title = getString(R.string.title_settings)

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                findNavController().navigateUp()
                (requireActivity() as AppCompatActivity)
                    .supportActionBar?.title = getString(R.string.app_name)
            }
        }

        (requireActivity() as AppCompatActivity)
            .onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

}