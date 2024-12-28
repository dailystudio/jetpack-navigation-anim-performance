package com.dailystudio.navigation.animation.ui.compose

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.viewmodel.DataViewModel.Companion.LAYOUT_CARD
import com.dailystudio.navigation.animation.viewmodel.DataViewModel.Companion.LAYOUT_IV_TV

@Composable
fun SettingsPage(
    modifier: Modifier = Modifier,
    rippleEnabled: Boolean,
    useCard: Boolean,
    onRippleEnabledChanged: (newValue: Boolean) -> Unit = {},
    onUseCardChanged: (newValue: Boolean) -> Unit = {},
) {
    var showDialog by remember { mutableStateOf(false) }

    Log.d("SettingsPage", "ripple enabled: $rippleEnabled")
    Log.d("SettingsPage", "use card: $useCard")
    Column {
        Row(
            modifier = Modifier
                .height(64.dp)
                .clickable {
                    onRippleEnabledChanged(!rippleEnabled)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.weight(1f))

            Text(
                text = stringResource(R.string.settings_selectable_background),
                Modifier.weight(4f),
                fontWeight = FontWeight.SemiBold
            )

            Switch(
                rippleEnabled,
                null,
                Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .height(64.dp)
                .clickable {
                    showDialog = !showDialog
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.weight(1f))

            Column (
                Modifier.weight(5f)
            ){
                Text(
                    text = stringResource(R.string.settings_item_layout),
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = stringResource(R.string.settings_item_layout_desc)
                )
            }
        }
    }

    ItemLayoutSettingsDialog(
        showDialog = showDialog,
        title = stringResource(R.string.settings_item_layout),
        items = listOf(
            Pair(LAYOUT_IV_TV, stringResource(R.string.layout_option_iv_tv_compose)),
            Pair(LAYOUT_CARD, stringResource(R.string.layout_option_card_compose))
        ),
        itemSelected = if (useCard) LAYOUT_CARD else LAYOUT_IV_TV,
        onItemSelected = {
            showDialog = false
            onUseCardChanged(it == LAYOUT_CARD)
        },
        onDismiss = {
            showDialog = false
        }
    )
}

@Composable
fun ItemLayoutSettingsDialog(
    modifier: Modifier = Modifier,
    showDialog: Boolean,
    title: String,
    items: List<Pair<String, String>>,
    itemSelected: String,
    onItemSelected: (item: String) -> Unit,
    onDismiss: () -> Unit,
){
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismiss()
            },
            title = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(Modifier.selectableGroup()) {
                    items.forEach { text ->
                        Row(
                            Modifier.fillMaxWidth()
                                .height(56.dp)
                                .selectable(
                                    selected = (text.first == itemSelected),
                                    onClick = { onItemSelected(text.first) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text.first == itemSelected),
                                onClick = null
                            )
                            Text(
                                text = text.second,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text( text = stringResource(android.R.string.cancel) )
                }
            }
        )
    }
}