package com.dailystudio.navigation.animation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.data.ListData
import com.dailystudio.navigation.animation.ui.DataListAdapter

class PrimaryListFragment: Fragment() {

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
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        val listOfData = ('A'..'Z').map {
            ListData(
                buildString {
                    append("Item - ")
                    append(it)
                }
            )
        }

        recyclerView?.adapter = DataListAdapter(
            onItemClicked = { data ->
                Log.d("RV", "[RV]: data = $data")

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
        ).apply {
            submitList(listOfData)
        }

    }

}