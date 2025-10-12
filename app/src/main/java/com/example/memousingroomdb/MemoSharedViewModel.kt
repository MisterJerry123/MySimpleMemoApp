package com.example.memousingroomdb

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memousingroomdb.db.Memo
import com.example.memousingroomdb.db.MemoDatabase
import com.example.memousingroomdb.db.MemoList
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoSharedViewModel(application: Application): AndroidViewModel(application) {

    lateinit var memoList :LiveData<List<Memo>>
    private val fb = Firebase.firestore
    private val currentUserId = Firebase.auth.currentUser?.uid


    val db = MemoDatabase.getInstance(application)
    val allMemos: LiveData<List<Memo>> = db?.memoDao()!!.getAllMemosAsLiveData()

    init {
        if (db != null) {
            //firestore->db->livedata
            //db.memoDao().deleteAllMemo()
            if(currentUserId!=null){
                Firebase.firestore.collection("memo").document(currentUserId)
                    .get()
                    .addOnSuccessListener { result -> // 성공 시 'result'는 QuerySnapshot 객체입니다.

                        val wrapper = result.toObject(MemoList::class.java)
                        Log.d(TAG, "loadedMemo_wrapper:$wrapper")

                        val loadedMemo = wrapper?.value ?: emptyList()
                        Log.d(TAG, "loadedMemo:$loadedMemo")

                        db.memoDao().insertAllMemos(loadedMemo)
                        /*
                        for(i in 0 until loadedMemo.size){
                            db.memoDao().insertMemo(loadedMemo[i])

                        }
                        */
                    }
                    .addOnFailureListener { exception ->
                        Log.w(TAG, "Error getting documents.", exception)
                    }
            }


            //

            memoList=db.memoDao().getAllMemosAsLiveData()
        }

    }

    fun delete(memo: Memo) {
        viewModelScope.launch(Dispatchers.IO) {
            // 이전에 만들어둔 DAO의 트랜잭션 함수를 호출
            db?.memoDao()?.deleteAndReorderMemos(memo)
            _deleteMemo.postValue(memo)

        }
    }

    fun deleteAllMemo(){
        viewModelScope.launch {
            db?.memoDao()?.deleteAllMemo()
        }
    }
    fun changeMemo(memo:List<Memo>){
        viewModelScope.launch {
            db?.memoDao()?.replaceAllMemos(memo)
        }
    }
    fun saveMemo(uid:String){
        viewModelScope.launch(Dispatchers.IO) {
            // "memos" 컬렉션에 새로운 문서를 추가하고 memo 객체의 데이터를 저장
            fb.collection("memo").document(uid)
                .set(memoList)
                .addOnSuccessListener { documentReference ->
                    // 저장 성공 시
                    Log.d("FIRESTORE_SUCCESS", "새로운 메모 저장 성공!")
                }
                .addOnFailureListener { e ->
                    // 저장 실패 시
                    Log.w("FIRESTORE_ERROR", "메모 저장 실패", e)
                }
        }
    }

    private val _resultMemo = MutableLiveData<Memo>()
    val resultMemo: LiveData<Memo> get() = _resultMemo

    fun updateMemo(memo:Memo){
        _resultMemo.value = memo
    }
    private val _deleteMemo = MutableLiveData<Memo?>()
    val deleteMemo: LiveData<Memo?> get() = _deleteMemo

    fun getMemos(memo:Memo): MutableList<Memo>? {
        return db?.memoDao()?.search()
    }

}