package com.dailystudio.navigation.animation.dev

import android.util.Log

class DroppedMonitor: AbsFrameMonitor() {
    private var lastFrameTimeNanos: Long = 0
    private var droppedFramesCount: Int = 0

    override fun reset() {
        super.reset()
        droppedFramesCount = 0
    }

    override fun analyzeFrameData(frameTimeNanos: Long): Float? {

        if (lastFrameTimeNanos != 0L) {
            val timeDiffMillis = (frameTimeNanos - lastFrameTimeNanos) / 1_000_000

            val expectedFrameTimeMillis = 16.67
            if (timeDiffMillis > expectedFrameTimeMillis) {
                droppedFramesCount += ((timeDiffMillis / expectedFrameTimeMillis).toInt() - 1)
            }
        }

        lastFrameTimeNanos = frameTimeNanos

        return droppedFramesCount.toFloat()
    }

}