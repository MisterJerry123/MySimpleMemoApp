package com.example.memousingroomdb.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MemoDao {
    @Insert
    fun insertMemo(memo:Memo)

    @Query("SELECT * From memo")
    fun search():MutableList<Memo>

    @Delete
    fun deleteMemo(memo:Memo)

    @Update
    fun updateMemo(memo:Memo)

    @Query("SELECT COUNT(*) FROM memo")
    fun getItemCount(): Int
}