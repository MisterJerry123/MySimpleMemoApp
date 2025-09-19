package com.example.memousingroomdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memousingroomdb.databinding.ItemMemoBinding
import com.example.memousingroomdb.db.Memo

class MemoAdapter(private var memoList:List<Memo>?):RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {

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

    override fun getItemCount(): Int {

        if(memoList!=null){
            return memoList!!.size
        }
        else{
            return 0
        }
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(it,position)
        }
        val currentMemo = memoList?.get(position)
        if (currentMemo != null) {
            holder.bind(currentMemo,position)

        }
    }

    class MemoViewHolder(private val binding:ItemMemoBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(memo:Memo,pos:Int){
            binding.tvTitle.text=memo.title
            binding.tvDate.text=memo.date
            binding.tvNumber.text=(pos+1).toString()
        }

    }
}