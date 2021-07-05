package me.mednikov.todomatic.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.mednikov.todomatic.data.models.Priority
import me.mednikov.todomatic.data.models.TodoEntity

class SharedViewModel(): ViewModel() {

    val emptyData: MutableLiveData<Boolean> = MutableLiveData(false)
    val tasksCount: MutableLiveData<Int> = MutableLiveData(0)

    fun checkDataEmpty(data: List<TodoEntity>) {
        emptyData.value = data.isEmpty()
    }

    fun parsePriority(priority: String): Priority {
        return when (priority){
            "High Priority" -> {
                Priority.HIGH}
            "Medium Priority" -> {
                Priority.MEDIUM}
            "Low Priority" -> {
                Priority.LOW}
            else -> {
                Priority.LOW}
        }
    }

    fun validate(title: String, description: String): Boolean {
        return !title.isNullOrEmpty() && !description.isNullOrEmpty()
    }

    fun setTasks(data: List<TodoEntity>) {
        emptyData.value = data.isEmpty()
        tasksCount.value = data.size
    }

}