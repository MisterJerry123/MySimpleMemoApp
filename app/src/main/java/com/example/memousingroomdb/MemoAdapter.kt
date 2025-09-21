package com.example.memousingroomdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.memousingroomdb.databinding.ItemMemoBinding
import com.example.memousingroomdb.db.Memo

class MemoAdapter:ListAdapter<Memo,RecyclerView.ViewHolder>(DiffUtilCallback()){
    // {

    interface OnItemClickListener{
        fun onItemClick(view: View, pos:Int)
    }
    private var listener : OnItemClickListener? =null
    private lateinit var itemClickListener : OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.itemClickListener=listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        var binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MemoViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(it,position)
        }

        if(holder is MemoViewHolder){
            val currentMemo = getItem(position) as Memo
            holder.bind(currentMemo,position)
        }

    }


//    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
//
//    }

    class MemoViewHolder(private val binding:ItemMemoBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(memo:Memo,pos:Int){
            binding.tvTitle.text=memo.title
            binding.tvDate.text=memo.date
            binding.tvNumber.text=(pos+1).toString()
        }
    }

    fun deleteMemo(memo:Memo){
        val newList = currentList.toMutableList()
        newList.remove(memo)
        submitList(newList)
    }

    fun insertMemo(memo: Memo){
        val newList = currentList.toMutableList()
        newList.add(memo)
        submitList(newList)
    }
}