package com.dailystudio.navigation.animation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.data.ListData
import com.dailystudio.navigation.animation.ui.DataListAdapter

class SecondaryListFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView? = view.findViewById(android.R.id.list)
        recyclerView?.layoutManager = GridLayoutManager(requireContext(), 2)

        val listOfData = (1..50).map {
            ListData(
                buildString {
                    append("Item - ")
                    append(it)
                }
            )
        }

        recyclerView?.adapter = DataListAdapter().apply {
            submitList(listOfData)
        }
    }

}