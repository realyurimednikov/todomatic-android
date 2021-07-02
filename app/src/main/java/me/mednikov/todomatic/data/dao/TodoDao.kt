package me.mednikov.todomatic.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import me.mednikov.todomatic.data.models.TodoEntity

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAll(): LiveData<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: TodoEntity)

    @Update
    suspend fun update(entity: TodoEntity)
}