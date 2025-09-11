package com.example.memousingroomdb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.memousingroomdb.db.Memo
import com.example.memousingroomdb.db.MemoDatabase

class AddMemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_memo)


        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        val btnSave = findViewById<Button>(R.id.btn_save)
        val db = MemoDatabase.getInstance(this)
        val etTitle = findViewById<EditText>(R.id.et_title)
        val intent = Intent(this,MainActivity::class.java)

        btnCancel.setOnClickListener {
            finish()
            startActivity(intent)

        }
        btnSave.setOnClickListener {
            db?.memoDao()?.insertMemo(Memo(title=etTitle.text.toString(),date = "2025-09-10"))
            finish()
            startActivity(intent)
        }

    }
}