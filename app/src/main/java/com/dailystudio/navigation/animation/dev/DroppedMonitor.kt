package com.dailystudio.navigation.animation.dev

import android.util.Log

class DroppedMonitor: AbsFrameMonitor() {
    private var lastFrameTimeNanos: Long = 0
    private var droppedFramesCount: Int = 0

    override fun reset() {
        Log.d("DroppedMonitor", "Dropped reset")
        droppedFramesCount = 0
        Log.d("DroppedMonitor", "droppedFramesCount: $droppedFramesCount")
    }

    override fun analyzeFrameData(frameTimeNanos: Long): Float? {

        if (lastFrameTimeNanos != 0L) {
            val timeDiffMillis = (frameTimeNanos - lastFrameTimeNanos) / 1_000_000

            val expectedFrameTimeMillis = 16.67
            if (timeDiffMillis > expectedFrameTimeMillis) {
                Log.d("DroppedMonitor", "B droppedFramesCount: $droppedFramesCount")
                droppedFramesCount += ((timeDiffMillis / expectedFrameTimeMillis).toInt() - 1)
                Log.d("DroppedMonitor", "A droppedFramesCount: $droppedFramesCount")
            }
        }

        lastFrameTimeNanos = frameTimeNanos

        return droppedFramesCount.toFloat()
    }

}