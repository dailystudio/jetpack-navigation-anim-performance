package com.dailystudio.navigation.animation.ui.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dailystudio.navigation.animation.data.Item

@Composable
fun  AbsDataListPage(
    modifier: Modifier = Modifier,
    data: List<Item>,
    rippleEnabled: Boolean,
    onItemClick: (item: Item) -> Unit,
    itemContent: ItemComposable
) {
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(data) { item ->
            itemContent(Modifier, item, rippleEnabled, onItemClick)
        }
    }
}

@Composable
fun  AbsDataGridPage(
    modifier: Modifier = Modifier,
    cells: GridCells,
    data: List<Item>,
    rippleEnabled: Boolean,
    onItemClick: (item: Item) -> Unit,
    itemContent: ItemComposable
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid (
        modifier = modifier,
        columns = cells,
        state = gridState
    ) {
        items(data) { item ->
            itemContent(Modifier, item, rippleEnabled, onItemClick)
        }
    }
}
