package com.example.memousingroomdb.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Memo(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String="",
    val date:String="",
    val content:String="",
    var cnt:Int?=1,
):Serializable

data class MemoList(
    val value:List<Memo> = listOf()
)
