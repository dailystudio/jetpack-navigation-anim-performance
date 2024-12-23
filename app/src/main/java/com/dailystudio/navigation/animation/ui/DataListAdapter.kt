package com.dailystudio.navigation.animation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dailystudio.navigation.animation.R
import com.dailystudio.navigation.animation.data.Item

class DataListAdapter(
    private val itemLayout: Int = R.layout.layout_list_item_iv_tv_card_sel,
    private val itemSelectableId: Int = R.id.selectable,
    val onItemClicked: ((Item) -> Unit)? = null
) :
    ListAdapter<Item, DataListAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(
        itemView: View,
        selectableId: Int
    ) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.text)
        val selectable: View = itemView.findViewById(selectableId)
    }

    companion object {
        private val DIFF_CALLBACK: ItemCallback<Item> = object: ItemCallback<Item>() {

            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.text == newItem.text
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
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