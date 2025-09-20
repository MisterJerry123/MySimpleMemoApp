package com.example.memousingroomdb

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.memousingroomdb.db.Memo

class MemoSharedViewModel: ViewModel() {
    val resultMemo = MutableLiveData<Memo>()

    fun postResult(memo:Memo){
        resultMemo.value = memo
    }

}