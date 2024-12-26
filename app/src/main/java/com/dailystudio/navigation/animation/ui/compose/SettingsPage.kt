package com.dailystudio.navigation.animation.ui.compose

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import com.dailystudio.navigation.animation.R
import kotlinx.coroutines.launch

@Composable
fun SettingsPage(
    modifier: Modifier = Modifier,
    rippleEnabled: Boolean = false,
    useCard: Boolean = false,
    onRippleEnabledChanged: (newValue: Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    Log.d("SettingsPage", "ripple enabled: $rippleEnabled")
    Column {
        Row(
            modifier = Modifier.clickable {
                onRippleEnabledChanged(!rippleEnabled)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                Modifier.weight(1f)
            )

            Text(
                text = stringResource(R.string.settings_selectable_background),
                Modifier.weight(4f)
            )

            Switch(
                rippleEnabled,
                null,
                Modifier.weight(1f)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                Modifier.weight(1f)
            )
            Column (
                Modifier.weight(5f)
            ){
                Text(
                    text = stringResource(R.string.settings_item_layout),
                )
                Text(
                    text = stringResource(R.string.settings_item_layout_desc)
                )
            }

        }
    }

}