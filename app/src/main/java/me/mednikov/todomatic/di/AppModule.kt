package me.mednikov.todomatic.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.mednikov.todomatic.data.TodoDatabase
import me.mednikov.todomatic.data.dao.TodoDao
import me.mednikov.todomatic.data.repositories.ITodoRepository
import me.mednikov.todomatic.data.repositories.TodoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext application: Context): TodoDatabase {
        return Room.databaseBuilder(application, TodoDatabase::class.java, "todo_db").build()
    }

    @Singleton
    @Provides
    fun provideRepository(db: TodoDatabase): ITodoRepository{
        val dao: TodoDao = db.todoDao()
        return TodoRepository(dao)
    }

}