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
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.data.Item
import com.dailystudio.navigation.animation.data.ListData
import com.dailystudio.navigation.animation.ui.DataListAdapter
import com.dailystudio.navigation.animation.viewmodel.DataViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class AbsListFragment: Fragment() {

    private var recyclerView: RecyclerView? = null
    private var dataAdapter: DataListAdapter? = null

    protected lateinit var viewModel: DataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[DataViewModel::class.java]

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                getListDataSource().collectLatest { listData ->
                    bindListData(listData)
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
        recyclerView?.layoutManager = getLayoutManager()
    }

    protected open fun bindListData(listData: ListData) {
        dataAdapter = DataListAdapter(
            itemLayout = listData.itemLayout.layoutId,
            itemSelectableId = listData.itemLayout.selectableId,
            onItemClicked = { data ->
                onListItemClicked(data = data)
            }
        )

        recyclerView?.adapter = dataAdapter

        dataAdapter?.submitList(listData.items)
    }

    abstract fun getLayoutManager(): LayoutManager
    abstract fun getListDataSource(): Flow<ListData>
    abstract fun onListItemClicked(data: Item)

}