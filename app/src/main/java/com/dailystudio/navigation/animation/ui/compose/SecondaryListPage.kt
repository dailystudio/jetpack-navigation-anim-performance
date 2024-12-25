package com.dailystudio.navigation.animation.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.dailystudio.navigation.animation.data.Item
import com.dailystudio.navigation.animation.data.ListData
import com.dailystudio.navigation.animation.fragment.SecondaryListFragment
import com.dailystudio.navigation.animation.ui.compose.utils.activityViewModel
import com.dailystudio.navigation.animation.viewmodel.DataViewModel

@Composable
fun SecondaryListPage(
    modifier: Modifier = Modifier,
    onItemClick: (item: Item) -> Unit,
    itemContent: @Composable (item: Item, modifier: Modifier) -> Unit
) {
    val viewModel = activityViewModel<DataViewModel>()
    val listData by viewModel.secondaryList.collectAsState(ListData())

    AbsDataListPage(
        modifier = Modifier,
        listData.items,
        onItemClick,
        itemContent,
    )
}