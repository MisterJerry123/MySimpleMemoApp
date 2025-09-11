package com.example.memousingroomdb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Memo::class], version = 1)
abstract class MemoDatabase:RoomDatabase() {
    abstract fun memoDao():MemoDao
    //싱클톤 패턴으로 구현
    companion object{
        @Volatile
        private var instance:MemoDatabase?=null
        fun getInstance(context: Context):MemoDatabase?{
            if(instance==null){
                synchronized(MemoDatabase::class){
                    instance = Room.databaseBuilder(
                        context,
                        MemoDatabase::class.java,
                        "MemoDatabase"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance

        }
    }


}