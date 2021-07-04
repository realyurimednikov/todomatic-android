package me.mednikov.todomatic.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import me.mednikov.todomatic.data.models.Priority
import me.mednikov.todomatic.data.models.TodoEntity
import me.mednikov.todomatic.data.repositories.ITodoRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TodoViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TodoViewModel
    @Mock private lateinit var repository: ITodoRepository

    @Before
    fun setup(){
        viewModel = TodoViewModel(repository)
    }

    @Test
    fun nonNullTest(){
        Assert.assertNotNull(viewModel)
    }

    @Test
    fun getAllTest(){
        val list:List<TodoEntity> = listOf(TodoEntity(1, "My title", Priority.MEDIUM, "My description"))
        val liveData:MutableLiveData<List<TodoEntity>> = MutableLiveData<List<TodoEntity>>()
        liveData.value = list
        Mockito.`when`(repository.getAll()).thenReturn(liveData)

        viewModel.getAll().observeForever{
            Assert.assertNotNull(it)
            Assert.assertEquals(1, it.size)
        }
    }

    @Test
    fun searchTest(){
        val query = "My"
        val list:List<TodoEntity> = listOf(TodoEntity(1, "My title", Priority.MEDIUM, "My description"))
        val liveData:MutableLiveData<List<TodoEntity>> = MutableLiveData<List<TodoEntity>>()
        liveData.value = list
        Mockito.`when`(repository.search(query)).thenReturn(liveData)

        viewModel.search(query).observeForever{
            Assert.assertNotNull(it)
            Assert.assertEquals(1, it.size)
        }
    }
}