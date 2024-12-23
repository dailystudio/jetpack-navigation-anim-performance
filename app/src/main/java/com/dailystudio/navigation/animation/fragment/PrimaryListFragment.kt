package com.dailystudio.navigation.animation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.ui.DataListAdapter
import com.dailystudio.navigation.animation.viewmodel.DataViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PrimaryListFragment: Fragment() {

    private var recyclerView: RecyclerView? = null
    private var dataAdapter: DataListAdapter? = null

    private lateinit var viewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[DataViewModel::class.java]

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.primaryList.collectLatest { it ->
                    dataAdapter?.submitList(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(android.R.id.list)
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        dataAdapter = DataListAdapter(
            onItemClicked = { _ ->
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
        )

        recyclerView?.adapter = dataAdapter
    }

}