package com.dailystudio.navigation.animation.dev

import android.view.Choreographer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

data class FrameData(
    val value: Float = 0f,
    val minValue: Float = Float.MAX_VALUE,
    val maxValue: Float = Float.MIN_VALUE,
) {
    override fun toString(): String {
        return buildString {
            append(value.roundToInt())

            val min = if (minValue == Float.MAX_VALUE) {
                0
            } else {
                minValue.roundToInt()
            }
            val max = if (maxValue == Float.MIN_VALUE) {
                0
            } else {
                maxValue.roundToInt()
            }

            append(" [")
            append("$min - $max")
            append("]")
        }
    }
}

abstract class AbsFrameMonitor() {

    private val _frameData = MutableStateFlow(FrameData())
    val frameData = _frameData

    open fun start() {
        Choreographer.getInstance().postFrameCallback(frameCallback)
    }

    private val frameCallback = object : Choreographer.FrameCallback {

        override fun doFrame(frameTimeNanos: Long) {
            analyzeFrameData(frameTimeNanos)?.let {
                val oldData = _frameData.value

                _frameData.value = FrameData(
                    value = it,
                    minValue = minOf(oldData.minValue, it),
                    maxValue = maxOf(oldData.maxValue, it),
                )
            }

            Choreographer.getInstance().postFrameCallback(this)
        }

    }

    open fun stop() {
        Choreographer.getInstance().removeFrameCallback(frameCallback)
    }

    open fun reset() {
        _frameData.value = FrameData()
    }

    abstract fun analyzeFrameData(frameTimeNanos: Long): Float?
}