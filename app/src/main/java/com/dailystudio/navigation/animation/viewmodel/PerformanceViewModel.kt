package com.dailystudio.navigation.animation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dailystudio.navigation.animation.dev.DroppedMonitor
import com.dailystudio.navigation.animation.dev.FPSMonitor

class PerformanceViewModel(application: Application): AndroidViewModel(application) {

    private val fpsMonitor = FPSMonitor().apply {
        start()
    }

    private val droppedMonitor = DroppedMonitor().apply {
        start()
    }

    val fps = fpsMonitor.frameData
    val droppedFrames = droppedMonitor.frameData

    fun resetDroppedFrames() {
        droppedMonitor.reset()
    }

    override fun onCleared() {
        super.onCleared()

        fpsMonitor.stop()
        droppedMonitor.stop()
    }

}