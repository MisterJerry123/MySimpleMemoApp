package com.example.memousingroomdb

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memousingroomdb.databinding.ActivityMainBinding
import com.example.memousingroomdb.db.Memo
import com.example.memousingroomdb.db.MemoList
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: MemoSharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, AddMemoActivity::class.java)
        val adapter = MemoAdapter()
        sharedViewModel.memoList.observe(this) { memos ->
            adapter.submitList(memos)
            if (memos.isEmpty()) {
                binding.floTop.visibility = View.INVISIBLE
                binding.rcvMemo.visibility = View.INVISIBLE
                binding.tvMemoEmptyMsg.visibility = View.VISIBLE
            } else {
                binding.rcvMemo.visibility = View.VISIBLE
                binding.floTop.visibility = View.VISIBLE

                binding.tvMemoEmptyMsg.visibility = View.INVISIBLE
            }
        }

        binding.btnLoad.setOnClickListener {
            //load
            Firebase.firestore.collection("memo").document("savedMemo")
                .get()
                .addOnSuccessListener { result -> // 성공 시 'result'는 QuerySnapshot 객체입니다.

                    val wrapper = result.toObject(MemoList::class.java)
                    Log.d(TAG, "loadedMemo_wrapper:$wrapper")

                    val loadedMemo = wrapper?.value ?: emptyList()
                    Log.d(TAG, "loadedMemo:$loadedMemo")

                    sharedViewModel.changeMemo(loadedMemo)
                    Toast.makeText(this, "저장된 메모를 성공적으로 불러왔습니다. ", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
        binding.btnSave.setOnClickListener {
            sharedViewModel.saveMemo()
            Toast.makeText(this, "메모가 성공적으로 저장되었습니다. ", Toast.LENGTH_SHORT).show()

        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val memo = adapter.currentList[position]
                // ViewModel에 삭제 요청
                sharedViewModel.delete(memo)
            }

        }
        //

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rcvMemo)

        binding.btnAdd.setOnClickListener {
            startActivity(intent)
            finish()
        }

        val recyclerView = binding.rcvMemo

        adapter.setOnItemClickListener(object : MemoAdapter.OnItemClickListener {
            override fun onItemClick(memo: Memo) {
                val detailMemoFragment = DetailMemoFragment.newInstance(memo)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fcv, detailMemoFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        observDeleteSignal(adapter)

    }

    private fun observDeleteSignal(adapter: MemoAdapter) {
        sharedViewModel.deleteMemo.observe(this) { memo ->
            memo?.let {
                supportFragmentManager.popBackStack()
                adapter.deleteMemo(memo)
            }
        }
    }
}