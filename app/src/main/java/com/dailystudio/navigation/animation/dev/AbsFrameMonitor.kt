package com.dailystudio.navigation.animation.dev

import android.view.Choreographer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class AbsFrameMonitor() {

    private val _frameData = MutableStateFlow(0f)
    val frameData = _frameData.asStateFlow()

    open fun start() {
        Choreographer.getInstance().postFrameCallback(frameCallback)
    }

    private val frameCallback = object : Choreographer.FrameCallback {

        override fun doFrame(frameTimeNanos: Long) {
            analyzeFrameData(frameTimeNanos)?.let {
                _frameData.value = it
            }

            Choreographer.getInstance().postFrameCallback(this)
        }

    }

    open fun stop() {
        Choreographer.getInstance().removeFrameCallback(frameCallback)
    }

    open fun reset() {

    }

    abstract fun analyzeFrameData(frameTimeNanos: Long): Float?
}