package com.dailystudio.navigation.animation.data

import com.dailystudio.navigation.animation.R

data class ItemLayout(
    val layoutId: Int = R.layout.layout_list_item,
    val selectableId: Int = R.id.text,
    val rippleEnabled: Boolean = false,
    val useCard: Boolean = false,
) {
    override fun toString(): String {
        return buildString {
            append("ripple = ${rippleEnabled}, ")
            append("card = ${useCard}, ")
            append("layout = ${layoutId}, ")
            append("selectable = $selectableId")
        }
    }
}