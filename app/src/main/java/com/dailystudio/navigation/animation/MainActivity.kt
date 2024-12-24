package com.dailystudio.navigation.animation

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.dailystudio.navigation.animation.viewmodel.PerformanceViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var performanceViewModel: PerformanceViewModel

    private var fpsView: TextView? = null
    private var droppedView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val topBar: Toolbar? = findViewById(R.id.topAppBar)

        topBar?.let {
            setSupportActionBar(it)
        }

        performanceViewModel = ViewModelProvider(this)[PerformanceViewModel::class.java]

        setupMonitors()

    }

    private fun setupMonitors() {
        fpsView = findViewById(R.id.fps)
        droppedView = findViewById(R.id.dropped)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                performanceViewModel.fps.collectLatest { fps ->
                    Log.d("MainActivity", "FPS: $fps")

                    fpsView?.text = buildString {
                        append("FPS: ")
                        append(fps.value.roundToInt())

                        val min = if (fps.minValue == Float.MAX_VALUE) {
                            0
                        } else {
                            fps.minValue.roundToInt()
                        }
                        val max = if (fps.maxValue == Float.MIN_VALUE) {
                            0
                        } else {
                            fps.maxValue.roundToInt()
                        }

                        append(" [")
                        append("$min - $max")
                        append("]")
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                performanceViewModel.droppedFrames.collectLatest { dropped ->
                    Log.d("MainActivity", "Dropped: $dropped")

                    droppedView?.text = buildString {
                        append("Dropped: ")
                        append(dropped.value.roundToInt())

                        val min = if (dropped.minValue == Float.MAX_VALUE) {
                            0
                        } else {
                            dropped.minValue.roundToInt()
                        }
                        val max = if (dropped.maxValue == Float.MIN_VALUE) {
                            0
                        } else {
                            dropped.maxValue.roundToInt()
                        }

                        append(" [")
                        append("$min - $max")
                        append("]")
                    }
                }
            }
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
                gotoSettings()
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

}