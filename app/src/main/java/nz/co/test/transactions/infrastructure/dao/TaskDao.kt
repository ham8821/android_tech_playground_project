package nz.co.test.transactions.infrastructure.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import nz.co.test.transactions.infrastructure.model.Task

@Dao
interface TaskDao {
    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM task_table ORDER BY date DESC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE id = :taskId")
    suspend fun getTask(taskId: Int): Task?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Delete
    fun deleteTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("SELECT COUNT(id) FROM task_table")
    fun getCount(): Int
}