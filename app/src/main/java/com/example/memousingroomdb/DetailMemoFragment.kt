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
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import com.example.memousingroomdb.databinding.FragmentDetailMemoBinding
import com.example.memousingroomdb.db.Memo
import com.example.memousingroomdb.db.MemoDatabase
import java.time.LocalDate


class DetailMemoFragment : Fragment() {

    private val sharedViewModel: MemoSharedViewModel by activityViewModels()

    private var _binding: FragmentDetailMemoBinding? = null
    private val binding get() = _binding!!
    private var memo: Memo? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMemoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener(
            "UpdateMemoFragment",
            this
        ) { requestKey, bundle ->
            val isFromUpdateMemoFragment = bundle.getBoolean("IsUpdateMemoFragment")
            if (isFromUpdateMemoFragment) {
                sharedViewModel.resultMemo.observe(viewLifecycleOwner) { result ->

                    binding.tvMemoTitle.text = result.title
                    binding.tvMemoDate.text = result.date
                    if (result.content.isBlank()) {
                        binding.tvMemoContent.text = "내용이 없습니다."
                    } else {
                        binding.tvMemoContent.text = result.content
                    }
                    memo = result

                }
            }
        }
        memo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("clickedMemo", Memo::class.java)
        } else {
            arguments?.getSerializable("clickedMemo") as? Memo
        }

        if (memo != null) {
            binding.tvMemoTitle.text = memo!!.title
            binding.tvMemoDate.text = memo!!.date
            if (memo!!.content.isBlank()) {
                binding.tvMemoContent.text = "내용이 없습니다."
            } else {
                binding.tvMemoContent.text = memo!!.content
            }
        }

        binding.btnMemoDelete.setOnClickListener {
            sharedViewModel.delete(memo!!)
            Toast.makeText(requireContext(), "메모가 지워졌어요", Toast.LENGTH_SHORT).show()
        }
        binding.btnMemoUpdate.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fcv, UpdateMemoFragment.newInstance(memo!!))
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
    companion object {
        fun newInstance(memo: Memo): DetailMemoFragment {
            val fragment = DetailMemoFragment()
            val args = Bundle()
            args.putSerializable("clickedMemo", memo)
            fragment.arguments = args
            return fragment
        }
    }
}