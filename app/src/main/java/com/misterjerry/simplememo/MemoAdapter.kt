package com.misterjerry.simplememo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.misterjerry.simplememo.databinding.ItemMemoBinding
import com.misterjerry.simplememo.db.Memo

class MemoAdapter : ListAdapter<Memo, RecyclerView.ViewHolder>(DiffUtilCallback()) {
    // {

    interface OnItemClickListener {
        fun onItemClick(memo: Memo)
    }

    private var listener: OnItemClickListener? = null
    private lateinit var itemClickListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        var binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMemo = getItem(position)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(currentMemo)
        }

        if (holder is MemoViewHolder) {
            holder.bind(currentMemo)
        }

    }


//    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
//
//    }

    class MemoViewHolder(private val binding: ItemMemoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(memo: Memo) {
            binding.tvTitle.text = memo.title
            binding.tvDate.text = memo.date
            binding.tvNumber.text = memo.cnt.toString()
        }
    }

    fun deleteMemo(memo: Memo) {
        val currentList = currentList.toMutableList()
        currentList.remove(memo)
        val newList = currentList.mapIndexed { index, item ->
            item.copy(cnt = index + 1)
        }
        submitList(newList)
    }

    fun insertMemo(memo: Memo) {
        val newList = currentList.toMutableList()
        newList.add(memo)
        submitList(newList)
    }
}