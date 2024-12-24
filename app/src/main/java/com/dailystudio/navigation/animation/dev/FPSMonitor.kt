package com.dailystudio.navigation.animation.dev

import android.util.Log

class FPSMonitor: AbsFrameMonitor() {

    private var frameCount = 0L
    private var lastTime = 0L

    override fun start() {
        lastTime = System.nanoTime()
        super.start()
    }

    override fun analyzeFrameData(frameTimeNanos: Long): Float? {
        frameCount++

        val currentTime = System.nanoTime()

        val delta = (currentTime - lastTime) / 1_000_000
        return if (delta >= 250) {
            val fps = frameCount * 1000f / delta

            frameCount = 0
            lastTime = frameTimeNanos

            fps
        } else {
            null
        }
    }

}