package me.mednikov.todomatic.data

import androidx.room.TypeConverter
import me.mednikov.todomatic.data.models.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority (value: String): Priority {
        return Priority.valueOf(value);
    }
}