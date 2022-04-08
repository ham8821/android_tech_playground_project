package nz.co.test.transactions.infrastructure.repository

import kotlinx.coroutines.flow.Flow
import nz.co.test.transactions.infrastructure.dao.TaskDao
import nz.co.test.transactions.infrastructure.dao.TransactionDao
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.infrastructure.model.Transaction
import javax.inject.Inject

class TaskLocalRepository @Inject constructor(private val taskDao: TaskDao){
    val allTask: Flow<List<Task>> = taskDao.getTasks()

    suspend fun addTask(task: Task){
        taskDao.insert(task)
    }

    suspend fun removeTask(task: Task){
        taskDao.deleteTask(task)
    }

    suspend fun removeAllTasks() {
        taskDao.deleteAll()
    }
}