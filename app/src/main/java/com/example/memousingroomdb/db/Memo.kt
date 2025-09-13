package com.example.memousingroomdb.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Memo(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val title:String,
    val date:String,
    val content:String
)
