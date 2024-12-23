package com.dailystudio.navigation.animation.data

import com.dailystudio.navigation.animation.R

data class ItemLayout(
    val layoutId: Int = R.layout.layout_list_item,
    val selectableId: Int = R.id.text,
) {
    override fun toString(): String {
        return buildString {
            append("layout = ${layoutId}, ")
            append("selectable = $selectableId")
        }
    }
}