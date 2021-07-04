package me.mednikov.todomatic.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import me.mednikov.todomatic.data.dao.TodoDao
import me.mednikov.todomatic.data.models.Priority
import me.mednikov.todomatic.data.models.TodoEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TodoRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock private lateinit var todoDao: TodoDao

    private lateinit var repository: TodoRepository

    private val list:List<TodoEntity> = listOf(TodoEntity(1, "My title", Priority.MEDIUM, "My description"))

    @Before
    fun setup(){
        repository = TodoRepository(todoDao)
    }

    @Test
    fun nonNullTest(){
        Assert.assertNotNull(repository)
    }

    @Test
    fun getAllTest(){
        val liveData: MutableLiveData<List<TodoEntity>> = MutableLiveData<List<TodoEntity>>()
        liveData.value = list

        Mockito.`when`(todoDao.getAll()).thenReturn(liveData)

        repository.getAll().observeForever{res ->
            Assert.assertNotNull(res)
            Assert.assertEquals(1, res.size)
        }
    }

    @Test
    fun searchTest(){
        val query = "My"
        val liveData: MutableLiveData<List<TodoEntity>> = MutableLiveData<List<TodoEntity>>()
        liveData.value = list

        Mockito.`when`(todoDao.search(query)).thenReturn(liveData)

        repository.search(query).observeForever{res ->
            Assert.assertNotNull(res)
            Assert.assertEquals(1, res.size)
        }
    }
}