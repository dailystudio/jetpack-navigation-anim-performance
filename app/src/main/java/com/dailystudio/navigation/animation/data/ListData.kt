package com.dailystudio.navigation.animation.data

class ListData(
    val items: List<Item> = emptyList(),
    val itemLayout: ItemLayout = ItemLayout()
) {
    override fun toString(): String {
        return buildString {
            append(this@ListData.javaClass.simpleName)
            append("[layout: $itemLayout],")
            append("[data: $items]")
        }
    }
}