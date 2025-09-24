package com.example.memousingroomdb

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class TouchHelperCallback(private val listener: ItemSwipeListener):ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.LEFT) {
    interface ItemSwipeListener {
        fun onItemSwiped(position: Int)
        // 필요하다면 onMove를 위한 fun onItemMoved(from, to)도 추가 가능
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        listener.onItemSwiped(position)
    }
}