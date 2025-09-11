package com.example.memousingroomdb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
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

        val intent = Intent(this,AddMemoActivity::class.java)
        val adapter = MemoAdapter(db?.memoDao()?.search())

        binding.btnAdd.setOnClickListener {
            startActivity(intent)
            finish()
        }

        val recyclerView = binding.rcvMemo
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter=adapter

    }
}