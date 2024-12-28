package com.dailystudio.navigation.animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.dailystudio.navigation.animation.ui.compose.Home
import com.dailystudio.navigation.animation.ui.theme.NavigationAnimationTheme
import com.dailystudio.navigation.animation.viewmodel.PerformanceViewModel

class MainComposeActivity: ComponentActivity()  {

    private lateinit var performanceViewModel: PerformanceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performanceViewModel = ViewModelProvider(this)[PerformanceViewModel::class.java]

        setContent {
            NavigationAnimationTheme {
                Home()
            }
        }
    }


    override fun onPause() {
        super.onPause()

        performanceViewModel.resetFps()
        performanceViewModel.resetDroppedFrames()
    }

}