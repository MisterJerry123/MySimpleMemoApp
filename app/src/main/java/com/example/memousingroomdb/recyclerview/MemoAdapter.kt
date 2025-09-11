package com.example.memousingroomdb.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memousingroomdb.databinding.ItemMemoBinding
import com.example.memousingroomdb.db.Memo

class MemoAdapter(private var memoList:List<Memo>?):RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {


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
        val currentMemo = memoList?.get(position)
        if (currentMemo != null) {
            holder.bind(currentMemo)
        }
    }

    class MemoViewHolder(private val binding:ItemMemoBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(memo:Memo){
            binding.tvTitle.text=memo.title
            binding.tvDate.text=memo.date
            binding.tvNumber.text=memo.id.toString()
        }

    }
}