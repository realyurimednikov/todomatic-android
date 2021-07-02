package me.mednikov.todomatic.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.mednikov.todomatic.data.TodoDatabase
import me.mednikov.todomatic.data.dao.TodoDao
import me.mednikov.todomatic.data.models.TodoEntity
import me.mednikov.todomatic.data.repositories.TodoRepository

class TodoViewModel(application: Application): AndroidViewModel(application) {

    private val dao: TodoDao = TodoDatabase.getDatabase(application).todoDao()
    private val repository: TodoRepository

    val getAll: LiveData<List<TodoEntity>>

    init {
        repository = TodoRepository(dao)
        getAll = repository.getData
    }

    fun insert(entity: TodoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(entity)
        }
    }

    fun update(entity: TodoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(entity)
        }
    }

    fun delete(entity: TodoEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(entity)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}