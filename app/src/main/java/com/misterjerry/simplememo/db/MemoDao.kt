package com.misterjerry.simplememo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface MemoDao {
    @Insert
    fun insertMemo(memo: Memo)

    @Query("SELECT * From memo")
    fun search(): MutableList<Memo>


    @Query("SELECT * FROM memo ORDER BY cnt ASC")
    fun getAllMemosAsLiveData(): LiveData<List<Memo>>

    @Delete
    fun deleteMemo(memo: Memo)

    @Update
    fun updateMemo(memo: Memo)

    @Query("SELECT COUNT(*) FROM memo")
    fun getItemCount(): Int


    @Transaction
    fun deleteAndReorderMemos(memo: Memo) {
        // 1단계: 메모 삭제
        deleteMemo(memo)

        // 2단계: 남은 메모들 가져오기
        val remainingMemos = search()

        // 3단계: 남은 메모들의 순번을 1부터 다시 매겨서 업데이트
        for ((index, item) in remainingMemos.withIndex()) {
            item.cnt = index + 1
            updateMemo(item)
        }
    }
}