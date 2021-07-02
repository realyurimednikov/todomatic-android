package me.mednikov.todomatic.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "todo_table")
@Parcelize
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
): Parcelable
