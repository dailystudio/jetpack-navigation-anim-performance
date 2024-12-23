package com.dailystudio.navigation.animation.fragment

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailystudio.navigation.animation.data.Item
import com.dailystudio.navigation.animation.data.ListData
import kotlinx.coroutines.flow.Flow

class SecondaryListFragment: AbsListFragment() {

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(requireContext(), 2)
    }

    override fun getListDataSource(): Flow<ListData> {
        return viewModel.secondaryList
    }

    override fun onListItemClicked(data: Item) {}

}