package com.example.memousingroomdb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.memousingroomdb.db.Memo
import com.example.memousingroomdb.db.MemoDao
import com.example.memousingroomdb.db.MemoDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = MemoDatabase.getInstance(this)


        val btn = findViewById<Button>(R.id.btn_add)
        val memoActivity = AddMemoActivity()
        val intent = Intent(this,AddMemoActivity::class.java)
        btn.setOnClickListener {
            startActivity(intent)
            finish()
        }
    }
}