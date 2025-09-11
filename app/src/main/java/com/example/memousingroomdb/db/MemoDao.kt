package com.example.memousingroomdb.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemoDao {
    @Insert
    fun insertMemo(memo:Memo)

    @Query("SELECT * From memo")
    fun search():List<Memo>


}