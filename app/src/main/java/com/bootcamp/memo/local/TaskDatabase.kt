package com.bootcamp.memo.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bootcamp.memo.model.TaskEntity

@Database(entities = [TaskEntity::class],version = 1)
abstract class TaskDatabase  : RoomDatabase() {
    abstract fun TaskDao() : TaskDao

    companion object{
        private var INSTANCE : TaskDatabase? = null

        fun getInstance(context : Context) : TaskDatabase?{
            if (INSTANCE == null ){
                synchronized(TaskDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,TaskDatabase::class.java,"task.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}
