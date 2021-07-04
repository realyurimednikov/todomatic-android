package me.mednikov.todomatic.data.repositories

import androidx.lifecycle.LiveData
import me.mednikov.todomatic.data.dao.TodoDao
import me.mednikov.todomatic.data.models.TodoEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(private val dao: TodoDao):ITodoRepository {

    override suspend fun insert(entity: TodoEntity) {
        dao.insert(entity)
    }

    override suspend fun update(entity: TodoEntity) {
        dao.update(entity)
    }

    override suspend fun delete(entity: TodoEntity){
        dao.delete(entity)
    }

    override suspend fun deleteAll(){
        dao.deleteAll()
    }

    override fun search(query: String): LiveData<List<TodoEntity>> {
        return dao.search(query)
    }

    override fun getAll(): LiveData<List<TodoEntity>> {
        return dao.getAll()
    }
}