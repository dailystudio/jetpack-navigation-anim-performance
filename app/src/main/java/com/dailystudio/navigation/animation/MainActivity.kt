package com.dailystudio.navigation.animation

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.dailystudio.navigation.animation.viewmodel.DataViewModel
import com.dailystudio.navigation.animation.viewmodel.PerformanceViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val performanceViewModel: PerformanceViewModel by viewModels()
    private val viewModel: DataViewModel by viewModels()

    private var fpsView: TextView? = null
    private var droppedView: TextView? = null
    private var framesDetail: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val topBar: Toolbar? = findViewById(R.id.topAppBar)

        topBar?.let {
            setSupportActionBar(it)
        }

        setupMonitors()
    }

    private fun setupMonitors() {
        fpsView = findViewById(R.id.fps)
        droppedView = findViewById(R.id.dropped)
        framesDetail = findViewById(R.id.frames_details)


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                performanceViewModel.debugFrames.collectLatest {
                    enableFramesDetails(it)
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                performanceViewModel.fps.collectLatest { fps ->
                    Log.d("MainActivity", "FPS: $fps")

                    fpsView?.text = buildString {
                        append(getString(R.string.label_fps))
                        append(": ")
                        append(fps)
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                performanceViewModel.droppedFrames.collectLatest { dropped ->
                    Log.d("MainActivity", "Dropped: $dropped")

                    droppedView?.text = buildString {
                        append(getString(R.string.label_dropped))
                        append(": ")
                        append(dropped)
                    }
                }
            }
        }
    }

    private fun enableFramesDetails(enable: Boolean) {
        framesDetail?.visibility = if (enable) {
            View.VISIBLE
        } else {
            View.GONE
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.action_settings -> {
                launchOrDelay(
                    lifecycleScope = lifecycleScope,
                    delayMillis = viewModel.settingsOfClickDelay(),
                ) {
                    gotoSettings()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun gotoSettings() {
        val naviController = findNavController(R.id.nav_host_fragment)
        if (naviController.currentDestination?.id == R.id.settingsFragment) {
            return
        }
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()

        performanceViewModel.resetDroppedFrames()
        performanceViewModel.resetFps()

        naviController.navigate(
            R.id.settingsFragment, null ,navOptions)
    }

    override fun onPause() {
        super.onPause()

        performanceViewModel.resetFps()
        performanceViewModel.resetDroppedFrames()
    }

}