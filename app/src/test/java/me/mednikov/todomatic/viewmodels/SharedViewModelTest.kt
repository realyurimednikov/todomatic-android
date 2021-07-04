package me.mednikov.todomatic.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import me.mednikov.todomatic.data.models.Priority
import me.mednikov.todomatic.data.models.TodoEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SharedViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SharedViewModel

    @Before
    fun setup(){
        viewModel = SharedViewModel()
    }

    @Test
    fun validateTest(){
        val title = "My title"
        val description = "My description"
        val result:Boolean = viewModel.validate(title, description)
        Assert.assertTrue(result)
    }

    @Test
    fun parsePriorityTest(){
        val priortyString = "High Priority"
        val priorityResult: Priority = viewModel.parsePriority(priortyString)
        Assert.assertEquals(Priority.HIGH, priorityResult)
    }

    @Test
    fun checkDataEmptyTest(){
        val emptyList:List<TodoEntity> = emptyList()
        viewModel.checkDataEmpty(emptyList)
        viewModel.emptyData.observeForever { result ->
            Assert.assertTrue(result)
        }

    }

    @Test
    fun checkDataNotEmptyTest(){
        val list:List<TodoEntity> = listOf(TodoEntity(1, "My title", Priority.MEDIUM, "My description"))
        viewModel.checkDataEmpty(list)
        viewModel.emptyData.observeForever { result ->
            Assert.assertFalse(result)
        }
    }
}