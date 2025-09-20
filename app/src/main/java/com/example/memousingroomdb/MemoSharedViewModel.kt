package com.example.memousingroomdb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.memousingroomdb.db.Memo

class MemoSharedViewModel: ViewModel() {
    private val _resultMemo = MutableLiveData<Memo>()
    val resultMemo: LiveData<Memo> get() = _resultMemo

    fun updateMemo(memo:Memo){
        _resultMemo.value = memo
    }

}