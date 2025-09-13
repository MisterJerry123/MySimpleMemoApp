package com.example.memousingroomdb

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memousingroomdb.databinding.ActivityMainBinding
import com.example.memousingroomdb.db.MemoDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = MemoDatabase.getInstance(this)
        val memoActivity = AddMemoActivity()
        val memoList = db?.memoDao()?.search()

        val intent = Intent(this,AddMemoActivity::class.java)
        val adapter = MemoAdapter(memoList)

        binding.btnAdd.setOnClickListener {
            startActivity(intent)
            finish()
        }

        val recyclerView = binding.rcvMemo

        adapter.setOnItemClickListener(object : MemoAdapter.OnItemClickListener{
            override fun onItemClick(view: View, pos: Int) {
                val detailMemoFragment = DetailMemoFragment.newInstance(memoList!![pos])
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fcv,detailMemoFragment)
                transaction.addToBackStack(null)
                transaction.commit()
                //Toast.makeText(view.context, memoList?.get(pos)!!.content, Toast.LENGTH_SHORT).show()
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter=adapter
    }
}