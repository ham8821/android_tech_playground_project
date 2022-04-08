package nz.co.test.transactions.infrastructure.services

import nz.co.test.transactions.infrastructure.model.Task
import retrofit2.http.GET

interface TaskService {
    @GET("test-data.json")
    suspend fun retrieveTask(): List<Task>
}