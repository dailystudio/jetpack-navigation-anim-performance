package com.dailystudio.navigation.animation.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.data.Item

typealias ItemComposable =
        @Composable (
            modifier: Modifier,
            item: Item,
            rippleEnabled: Boolean,
            onItemClick: ((item: Item) -> Unit)?
        ) -> Unit

fun Modifier.bindOnItemClick(
    item: Item,
    rippleEnabled: Boolean,
    onItemClick: ((item: Item) -> Unit)?
): Modifier = composed {
    if (onItemClick != null) {
        if (!rippleEnabled) {
            this.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {
                onItemClick(item)
            }
        } else {
            this.clickable {
                onItemClick(item)
            }
        }
    } else {
        this
    }
}

@Composable
fun SimpleItem(
    modifier: Modifier = Modifier,
    item: Item,
    rippleEnabled: Boolean,
    onItemClick: ((item: Item) -> Unit)? = null,
) {
    val newModifier = modifier
        .fillMaxWidth()
        .bindOnItemClick(item, rippleEnabled, onItemClick)
        .padding(8.dp)

    Row(
        modifier = newModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = rememberAsyncImagePainter(R.drawable.icon_placeholder),
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = item.text,
            fontSize = 18.sp, // Text size
            color = Color(0xFF333333), // Te
        )
    }
}

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    item: Item,
    rippleEnabled: Boolean,
    onItemClick: ((item: Item) -> Unit)? = null,
) {
    val newModifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
        .bindOnItemClick(item, rippleEnabled, onItemClick)

    Card(
        modifier = newModifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(5.dp) // 设置圆角
    ) {
        SimpleItem(modifier, item, rippleEnabled, null)
    }
}