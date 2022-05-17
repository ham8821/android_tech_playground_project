package nz.co.test.transactions.infrastructure.repository

import kotlinx.coroutines.flow.Flow
import nz.co.test.transactions.infrastructure.dao.TaskDao
import nz.co.test.transactions.infrastructure.model.Task
import javax.inject.Inject

class TaskLocalRepository @Inject constructor(private val taskDao: TaskDao){

    fun getTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun getTask(taskId: Int): Task? {
        return taskDao.getTask(taskId)
    }

    suspend fun addTask(task: Task){
        taskDao.insert(task)
    }

    suspend fun removeTask(task: Task){
        taskDao.deleteTask(task)
    }

    suspend fun removeAllTasks() {
        taskDao.deleteAll()
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }
}