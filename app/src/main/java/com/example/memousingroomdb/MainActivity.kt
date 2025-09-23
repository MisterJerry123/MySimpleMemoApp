package com.example.memousingroomdb

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memousingroomdb.databinding.ActivityMainBinding
import com.example.memousingroomdb.db.Memo
import com.example.memousingroomdb.db.MemoDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: MemoSharedViewModel by viewModels()
    private var memoList : MutableList<Memo>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = MemoDatabase.getInstance(this)
        memoList = db?.memoDao()?.search()

        val intent = Intent(this,AddMemoActivity::class.java)
        val adapter = MemoAdapter()
        adapter.submitList(memoList)


        binding.btnAdd.setOnClickListener {
            startActivity(intent)
            finish()
        }

        val recyclerView = binding.rcvMemo

        adapter.setOnItemClickListener(object : MemoAdapter.OnItemClickListener{
            override fun onItemClick(memo: Memo) {
                val detailMemoFragment = DetailMemoFragment.newInstance(memo)
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fcv,detailMemoFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter=adapter
        observDeleteSignal(adapter)

    }
    private fun observDeleteSignal(adapter:MemoAdapter){
        sharedViewModel.deleteMemo.observe(this){memo->
            memo?.let {
                supportFragmentManager.popBackStack()
                adapter.deleteMemo(memo)
//
//                adapter.notifyItemRemoved(memoId)
//                memoList?.removeAt(memoId)
//                adapter.notifyDataSetChanged()
            }
        }
    }
}