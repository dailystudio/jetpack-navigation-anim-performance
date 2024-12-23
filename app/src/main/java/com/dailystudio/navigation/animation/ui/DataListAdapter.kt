package com.dailystudio.navigation.animation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.data.ListData

class DataListAdapter(
    private val itemLayout: Int = R.layout.layout_list_item_iv_tv_card,
    private val itemSelectableId: Int = R.id.selectable,
    val onItemClicked: ((ListData) -> Unit)? = null
) :
    ListAdapter<ListData, DataListAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(
        itemView: View,
        selectableId: Int
    ) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text)
        val selectable: View = itemView.findViewById(selectableId)
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
            .inflate(itemLayout, parent, false) // 替换为你的 item 布局文件
        return ViewHolder(view, itemSelectableId)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)

        holder.textView.text = data.text
        holder.selectable.setOnClickListener {
            onItemClicked?.invoke(data)
        }
    }

}