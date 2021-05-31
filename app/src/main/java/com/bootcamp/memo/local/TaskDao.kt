package com.bootcamp.memo.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.bootcamp.memo.model.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity ")
    fun getAllTask() : List<TaskEntity>

    @Insert(onConflict = REPLACE)
    fun insertTask(taskEntity : TaskEntity)  : Long

    @Update
    fun updateTask(taskEntity: TaskEntity) : Int

    @Delete
    fun deleteTask(taskEntity: TaskEntity) : Int
}