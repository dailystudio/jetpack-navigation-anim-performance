package com.dailystudio.navigation.animation.fragment

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.data.Item
import com.dailystudio.navigation.animation.data.ListData
import com.dailystudio.navigation.animation.launchOrDelay
import com.dailystudio.navigation.animation.viewmodel.PerformanceViewModel
import kotlinx.coroutines.flow.Flow

class SecondaryListFragment: AbsListFragment() {

    private val performanceViewModel: PerformanceViewModel by viewModels({ requireActivity() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                performanceViewModel.resetDroppedFrames()
                performanceViewModel.resetFps()

                launchOrDelay(
                    lifecycleScope = lifecycleScope,
                    delayMillis = viewModel.settingsOfClickDelay(),
                ) {
                    findNavController().navigateUp()
                }
            }
        }

        (requireActivity() as AppCompatActivity)
            .onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(requireContext(), 2)
    }

    override fun getListDataSource(): Flow<ListData> {
        return viewModel.secondaryList
    }

    override fun onListItemClicked(data: Item) {}

}