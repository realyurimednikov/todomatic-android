package me.mednikov.todomatic.viewmodels

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import me.mednikov.todomatic.data.models.Priority

class SharedViewModel(application: Application): AndroidViewModel(application) {

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
        return !TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)
    }

    fun parsePriorityToInt (priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> {0}
            Priority.MEDIUM -> {1}
            Priority.LOW -> {2}
        }
    }
}