package com.example.memousingroomdb

import android.content.ContentValues.TAG
import android.content.Context
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
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: MemoSharedViewModel by viewModels()

    private val currentUserId = Firebase.auth.currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            if(currentUserId!=null){
                checkUserAndInitRoom(currentUserId)
            }
            else{
                throw IllegalStateException("FB로부터 uid를 수신받지 못함")
            }
        }catch (e: IllegalStateException){
            Log.e("AUTH_CHECK", "오류 발생: ${e.message}")
        }




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

            if(currentUserId!=null){
                //load
                Firebase.firestore.collection("memo").document(currentUserId)
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
            else{
                Toast.makeText(this, "UID값이 NULL입니다. ", Toast.LENGTH_SHORT).show()
            }


        }
        binding.btnSave.setOnClickListener {
            if(currentUserId!=null){
                sharedViewModel.saveMemo(currentUserId)
                Toast.makeText(this, "메모가 성공적으로 저장되었습니다. ", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "UID값이 NULL입니다. ", Toast.LENGTH_SHORT).show()
            }
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

    override fun onStop() {
        super.onStop()
        if(currentUserId!=null){
            sharedViewModel.saveMemo(currentUserId)

        }
        else{
            //TODO null 일때 오류
        }

    }

    private fun observDeleteSignal(adapter: MemoAdapter) {
        sharedViewModel.deleteMemo.observe(this) { memo ->
            memo?.let {
                supportFragmentManager.popBackStack()
                adapter.deleteMemo(memo)
            }
        }
    }
    private fun checkUserAndInitRoom(currentUserId:String) {//이전 사용자랑 비교 함수
        val prefs = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val lastUserIdKey = "last_user_id"
        val lastUserId = prefs.getString(lastUserIdKey, null)

        if (currentUserId != null && lastUserId != currentUserId) {

            sharedViewModel.deleteAllMemo()
        }
        if (currentUserId != null) {
            prefs.edit().putString(lastUserIdKey, currentUserId).apply()
        }
    }

    private fun saveBeforeExit(){
        val prefs = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val lastUserIdKey = "last_user_id"
        val lastUserId = prefs.getString(lastUserIdKey, null)
        if(lastUserId!=null){
            sharedViewModel.saveMemo(lastUserId)

        }
    }
}