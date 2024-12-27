package com.dailystudio.navigation.animation.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.dev.FrameData

@Composable
fun FramesMonitors(
    modifier: Modifier = Modifier,
    fps: FrameData,
    dropped: FrameData,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0x80000000))
            .padding(8.dp)
    ) {
        FrameMonitor(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.label_fps) ,
            frameData = fps
        )
        FrameMonitor(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.label_dropped) ,
            frameData = dropped
        )
    }
}
@Composable
fun FrameMonitor(
    modifier: Modifier = Modifier,
    label: String,
    frameData: FrameData,
) {
    Text(
        modifier = modifier,
        text = buildString {
            append(label)
            append(": ")
            append(frameData)
        },
        fontSize = 14.sp,
        color = Color.White
    )
}