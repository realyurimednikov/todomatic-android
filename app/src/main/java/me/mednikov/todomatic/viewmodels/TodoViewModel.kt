package me.mednikov.todomatic.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.mednikov.todomatic.data.models.TodoEntity
import me.mednikov.todomatic.data.repositories.ITodoRepository
import javax.inject.Inject

@HiltViewModel
class TodoViewModel
    @Inject
    constructor(private val repository: ITodoRepository): ViewModel() {

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

    fun search(query: String): LiveData<List<TodoEntity>>{
        return repository.search(query)
    }

    fun getAll(): LiveData<List<TodoEntity>>{
        return repository.getAll()
    }
}