package me.mednikov.todomatic.data.repositories

import androidx.lifecycle.LiveData
import me.mednikov.todomatic.data.dao.TodoDao
import me.mednikov.todomatic.data.models.TodoEntity

class TodoRepository(private val dao: TodoDao) {

    val getData: LiveData<List<TodoEntity>> = dao.getAll();

    suspend fun insert(entity: TodoEntity) {
        dao.insert(entity)
    }

    suspend fun update(entity: TodoEntity) {
        dao.update(entity)
    }

    suspend fun delete(entity: TodoEntity){
        dao.delete(entity)
    }

    suspend fun deleteAll(){
        dao.deleteAll()
    }
}