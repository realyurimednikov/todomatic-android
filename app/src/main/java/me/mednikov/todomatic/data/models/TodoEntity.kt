package me.mednikov.todomatic.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.mednikov.todomatic.data.models.Priority

@Entity(tableName = "todo_table")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
)
