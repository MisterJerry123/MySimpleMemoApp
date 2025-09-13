package com.example.memousingroomdb

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.memousingroomdb.databinding.ActivityAddMemoBinding
import com.example.memousingroomdb.db.Memo
import com.example.memousingroomdb.db.MemoDatabase
import java.time.LocalDate

class AddMemoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddMemoBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = MemoDatabase.getInstance(this)
        val intent = Intent(this,MainActivity::class.java)

        binding.btnCancel.setOnClickListener {
            finish()
            startActivity(intent)
        }

        binding.btnSave.setOnClickListener {

            if(binding.etTitle.text.isBlank()){
                Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                db?.memoDao()?.insertMemo(Memo(title=binding.etTitle.text.toString(),content = binding.etContent.text.toString(),date = LocalDate.now().toString()))
                finish()
                startActivity(intent)
            }

        }
    }
}