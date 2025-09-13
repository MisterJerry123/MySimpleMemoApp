package com.example.memousingroomdb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.room.Room
import com.example.memousingroomdb.databinding.ActivityMainBinding
import com.example.memousingroomdb.db.Memo
import com.example.memousingroomdb.db.MemoDao
import com.example.memousingroomdb.db.MemoDatabase
import com.example.memousingroomdb.recyclerview.MemoAdapter

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

                Toast.makeText(view.context, memoList?.get(pos)!!.content, Toast.LENGTH_SHORT).show()
            }
        })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter=adapter
    }
}