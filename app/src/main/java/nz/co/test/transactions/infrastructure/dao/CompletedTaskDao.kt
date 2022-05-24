package nz.co.test.transactions.infrastructure.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import nz.co.test.transactions.infrastructure.model.CompletedTask

@Dao
interface CompletedTaskDao {
    @Query("SELECT * FROM completed_task_table ORDER BY date DESC")
    fun getAllTasksCompleted(): Flow<List<CompletedTask>>

    @Query("SELECT * FROM completed_task_table WHERE id = :taskId")
    suspend fun getPreviousTask(taskId: Int): CompletedTask?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: CompletedTask)

    @Query("DELETE FROM completed_task_table")
    suspend fun deleteAll()

    @Delete
    fun deleteTask(task: CompletedTask)

    @Query("SELECT COUNT(id) FROM task_table")
    fun getCount(): Int
}