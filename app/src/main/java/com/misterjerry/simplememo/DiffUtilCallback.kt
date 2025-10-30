package com.misterjerry.simplememo

import androidx.recyclerview.widget.DiffUtil
import com.misterjerry.simplememo.db.Memo

class DiffUtilCallback : DiffUtil.ItemCallback<Memo>() {
    override fun areItemsTheSame(oldItem: Memo, newItem: Memo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Memo, newItem: Memo): Boolean {
        return oldItem == newItem
    }
}