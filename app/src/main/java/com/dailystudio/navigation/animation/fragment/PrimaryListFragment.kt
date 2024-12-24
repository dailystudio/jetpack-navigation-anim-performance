package com.dailystudio.navigation.animation.fragment

import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.data.Item
import com.dailystudio.navigation.animation.data.ListData
import com.dailystudio.navigation.animation.viewmodel.PerformanceViewModel
import kotlinx.coroutines.flow.Flow

class PrimaryListFragment: AbsListFragment() {

    private val performanceViewModel: PerformanceViewModel by viewModels({ requireActivity() })

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

        performanceViewModel.resetDroppedFrames()
        performanceViewModel.resetFps()

        findNavController().navigate(dir,
            navOptions = navOptions)
    }

}