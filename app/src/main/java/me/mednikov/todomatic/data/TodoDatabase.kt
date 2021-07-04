package me.mednikov.todomatic.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.mednikov.todomatic.data.dao.TodoDao
import me.mednikov.todomatic.data.models.TodoEntity

@Database(entities = [TodoEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(Converter::class)
abstract class TodoDatabase: RoomDatabase() {

    abstract fun todoDao(): TodoDao

}