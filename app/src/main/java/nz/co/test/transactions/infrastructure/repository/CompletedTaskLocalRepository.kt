package nz.co.test.transactions.infrastructure.repository

import kotlinx.coroutines.flow.Flow
import nz.co.test.transactions.infrastructure.dao.CompletedTaskDao
import nz.co.test.transactions.infrastructure.model.CompletedTask
import javax.inject.Inject

class CompletedTaskLocalRepository @Inject constructor(private val completedTaskDao: CompletedTaskDao){

    fun getTasks(): Flow<List<CompletedTask>> = completedTaskDao.getAllTasksCompleted()

    suspend fun getTask(taskId: Int): CompletedTask? {
        return completedTaskDao.getPreviousTask(taskId)
    }

    fun getNumCompletedTasks(): Int {
        return completedTaskDao.getCount()
    }

    suspend fun addTask(task: CompletedTask){
        completedTaskDao.insert(task)
    }

    suspend fun removeTask(task: CompletedTask){
        completedTaskDao.deleteTask(task)
    }

    suspend fun removeAllTasks() {
        completedTaskDao.deleteAll()
    }
}