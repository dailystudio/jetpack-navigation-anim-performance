package com.dailystudio.navigation.animation.fragment

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.launchOrDelay
import com.dailystudio.navigation.animation.viewmodel.DataViewModel


class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: DataViewModel by viewModels( { requireActivity() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as AppCompatActivity)
            .supportActionBar?.title = getString(R.string.title_settings)

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                launchOrDelay(
                    lifecycleScope = lifecycleScope,
                    delayMillis = viewModel.settingsOfClickDelay(),
                ) {
                    findNavController().navigateUp()
                    (requireActivity() as AppCompatActivity)
                        .supportActionBar?.title = getString(R.string.app_name)
                }
            }
        }

        (requireActivity() as AppCompatActivity)
            .onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

}