package com.dailystudio.navigation.animation.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.dailystudio.navigation.animation.data.Item

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun  AbsDataListPage(
    modifier: Modifier = Modifier,
    data: List<Item>,
    onItemClick: (item: Item) -> Unit,
    itemContent: @Composable (item: Item, modifier: Modifier) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(data) { item ->
            Box(
                modifier = modifier
                    .combinedClickable (
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {
                            onItemClick(item)
                        },
                    )
            ) {
                itemContent(item, Modifier)
            }
        }
    }
}
