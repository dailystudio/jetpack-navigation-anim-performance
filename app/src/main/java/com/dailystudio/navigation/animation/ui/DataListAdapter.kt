package com.dailystudio.navigation.animation.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.data.ListData

class DataListAdapter(val onItemClicked: ((ListData) -> Unit)? = null) :
    ListAdapter<ListData, DataListAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text)
        val selectable: View = itemView.findViewById<View?>(R.id.selectable)
    }

    companion object {
        const val TAG = "DataListAdapter"

        private val DIFF_CALLBACK: ItemCallback<ListData> = object: ItemCallback<ListData>() {
            override fun areItemsTheSame(oldItem: ListData, newItem: ListData): Boolean {
                return oldItem.text == newItem.text
            }

            override fun areContentsTheSame(oldItem: ListData, newItem: ListData): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_list_item, parent, false) // 替换为你的 item 布局文件
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)

        holder.textView.text = data.text
        Log.d("RV", "[RV]: position = $position, text = ${data.text}")
        holder.selectable.setOnClickListener {
            onItemClicked?.invoke(data)
        }

    }

}