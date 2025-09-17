package com.example.memousingroomdb

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.memousingroomdb.databinding.FragmentDetailMemoBinding
import com.example.memousingroomdb.databinding.FragmentUpdateMemoBinding
import com.example.memousingroomdb.db.Memo
import com.example.memousingroomdb.db.MemoDao
import com.example.memousingroomdb.db.MemoDatabase
import java.time.LocalDate


class UpdateMemoFragment : Fragment() {
    private var _binding:FragmentUpdateMemoBinding?=null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateMemoBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = MemoDatabase.getInstance(requireContext())

        val memo = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            arguments?.getSerializable("clickedMemo",Memo::class.java)
        }
        else{
            arguments?.getSerializable("clickedMemo") as? Memo
        }


        if(memo!=null){
            binding.etTitle.setText(memo.title)
            binding.etContent.setText(memo.content)
        }

        binding.btnMemoCancel.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()

        }

        binding.btnMemoUpdate.setOnClickListener {
            db?.memoDao()?.updateMemo(Memo(id= memo!!.id,title = binding.etTitle.text.toString(), date = "${LocalDate.now()} 수정됨", content = binding.etContent.text.toString()))

            requireActivity().supportFragmentManager.popBackStack()

        }

    }

    companion object{
        fun newInstance(memo: Memo):UpdateMemoFragment{
            val fragment = UpdateMemoFragment()
            val args = Bundle()
            args.putSerializable("clickedMemo",memo)
            fragment.arguments = args
            return fragment
        }
    }


}