package com.dailystudio.navigation.animation.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.dailystudio.navigation.animation.dev.DroppedMonitor
import com.dailystudio.navigation.animation.dev.FPSMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PerformanceViewModel(application: Application): AndroidViewModel(application) {

    companion object {

        private const val TAG = "PerformanceViewModel"

        private const val PREF_DEBUG_FRAMES = "debug_frames"
    }

    private val _settingsPrefs = PreferenceManager.getDefaultSharedPreferences(application)

    private fun debugFrames(): Boolean {
        return _settingsPrefs.getBoolean(PREF_DEBUG_FRAMES, false)
    }

    private val fpsMonitor = FPSMonitor().apply {
        start()
    }

    private val droppedMonitor = DroppedMonitor().apply {
        start()
    }

    val fps = fpsMonitor.frameData
    val droppedFrames = droppedMonitor.frameData

    private val _debugFrames: MutableStateFlow<Boolean> =
        MutableStateFlow(debugFrames())

    val debugFrames: StateFlow<Boolean> = _debugFrames.asStateFlow()

    private val _settingsPrefsChangedListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            Log.d(TAG, "[${this@PerformanceViewModel}] Pref[$key] changed")
            when (key) {
                PREF_DEBUG_FRAMES -> {
                    _debugFrames.value = debugFrames()
                }
            }
        }

    init {
        _settingsPrefs.registerOnSharedPreferenceChangeListener(_settingsPrefsChangedListener)
    }
    fun resetDroppedFrames() {
        droppedMonitor.reset()
    }

    fun resetFps() {
        fpsMonitor.reset()
    }

    override fun onCleared() {
        super.onCleared()

        fpsMonitor.stop()
        droppedMonitor.stop()
    }

    fun updateDebugFrames(rippleEnabled: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _settingsPrefs.edit().putBoolean(
                    PREF_DEBUG_FRAMES,
                    rippleEnabled
                ).apply()
            }
        }
    }
}