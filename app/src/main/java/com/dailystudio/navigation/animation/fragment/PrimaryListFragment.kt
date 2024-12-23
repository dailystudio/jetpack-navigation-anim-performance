package com.dailystudio.navigation.animation.fragment

import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.data.Item
import com.dailystudio.navigation.animation.data.ListData
import kotlinx.coroutines.flow.Flow

class PrimaryListFragment: AbsListFragment() {

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(requireContext())
    }

    override fun getListDataSource(): Flow<ListData> {
        return viewModel.primaryList
    }

    override fun onListItemClicked(data: Item) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()

        val dir = PrimaryListFragmentDirections.actionPrimaryListFragmentToSecondaryListFragment()

        findNavController().navigate(dir,
            navOptions = navOptions)
    }

}