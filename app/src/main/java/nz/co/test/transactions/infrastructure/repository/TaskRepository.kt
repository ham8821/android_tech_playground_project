package nz.co.test.transactions.infrastructure.repository

import nz.co.test.transactions.infrastructure.services.TaskService
import nz.co.test.transactions.infrastructure.services.TransactionsService
import javax.inject.Inject

class TaskRepository @Inject constructor(private val tasksService: TaskService) {
    suspend fun retrieveTasks() = tasksService.retrieveTask()
}