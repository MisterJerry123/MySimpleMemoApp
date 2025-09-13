package com.example.memousingroomdb

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.memousingroomdb.databinding.FragmentDetailMemoBinding
import com.example.memousingroomdb.db.Memo
import com.example.memousingroomdb.db.MemoDao
import com.example.memousingroomdb.db.MemoDatabase


class DetailMemoFragment : Fragment() {
    private var _binding:FragmentDetailMemoBinding?=null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMemoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val memo = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            arguments?.getSerializable("clickedMemo",Memo::class.java)
        }
        else{
            arguments?.getSerializable("clickedMemo") as? Memo
        }


        if(memo!=null){
            binding.tvMemoTitle.text = "${memo.title}"
            binding.tvMemoDate.text = "${memo.date}"
            binding.tvMemoContent.text = memo.content
        }

        binding.btnMemoDelete.setOnClickListener {


        }

    }

    companion object{
        fun newInstance(memo: Memo):DetailMemoFragment{
            val fragment = DetailMemoFragment()
            val args = Bundle()
            args.putSerializable("clickedMemo",memo)
            fragment.arguments = args
            return fragment
        }
    }


}