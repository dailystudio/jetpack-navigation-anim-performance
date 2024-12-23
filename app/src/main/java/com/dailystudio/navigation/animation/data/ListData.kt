package com.dailystudio.navigation.animation.data

class ListData(
    val items: List<Item>,
    val itemLayout: ItemLayout
) {
    override fun toString(): String {
        return buildString {
            append(this@ListData.javaClass.simpleName)
            append("[layout: $itemLayout],")
            append("[data: $items]")
        }
    }
}