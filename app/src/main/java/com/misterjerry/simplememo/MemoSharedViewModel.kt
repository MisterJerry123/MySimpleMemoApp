package com.misterjerry.simplememo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.misterjerry.simplememo.db.Memo
import com.misterjerry.simplememo.db.MemoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoSharedViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var memoList: LiveData<List<Memo>>
    val db = MemoDatabase.getInstance(application)
    val allMemos: LiveData<List<Memo>> = db?.memoDao()!!.getAllMemosAsLiveData()

    init {
        if (db != null) {
            memoList = db.memoDao().getAllMemosAsLiveData()
        }

    }

    fun delete(memo: Memo) {
        viewModelScope.launch(Dispatchers.IO) {
            // 이전에 만들어둔 DAO의 트랜잭션 함수를 호출
            db?.memoDao()?.deleteAndReorderMemos(memo)
            _deleteMemo.postValue(memo)

        }
    }

    private val _resultMemo = MutableLiveData<Memo>()
    val resultMemo: LiveData<Memo> get() = _resultMemo

    fun updateMemo(memo: Memo) {
        _resultMemo.value = memo
    }

    private val _deleteMemo = MutableLiveData<Memo?>()
    val deleteMemo: LiveData<Memo?> get() = _deleteMemo

    fun getMemos(memo: Memo): MutableList<Memo>? {
        return db?.memoDao()?.search()
    }

}