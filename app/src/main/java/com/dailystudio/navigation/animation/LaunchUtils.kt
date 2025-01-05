package com.dailystudio.navigation.animation

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun launchOrDelay(lifecycleScope: LifecycleCoroutineScope,
                  delayMillis: Long,
                  block: () -> Unit) {
    if (delayMillis > 0) {
        lifecycleScope.launch {
            delay(delayMillis)
            block.invoke()
        }
    } else {
        block.invoke()
    }
}