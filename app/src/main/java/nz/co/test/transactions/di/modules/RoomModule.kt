package nz.co.test.transactions.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nz.co.test.transactions.infrastructure.TaskRoomDataBase
import nz.co.test.transactions.infrastructure.dao.CompletedTaskDao
import nz.co.test.transactions.infrastructure.dao.TaskDao
import nz.co.test.transactions.infrastructure.repository.CompletedTaskLocalRepository
import nz.co.test.transactions.infrastructure.repository.TaskLocalRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RoomModule() {

    @Singleton
    @Provides
    fun providesTaskRoomDatabase(mApplication: Application): TaskRoomDataBase {
        return Room.databaseBuilder(mApplication, TaskRoomDataBase::class.java, "task_database")
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesTaskProductDao(demoDatabase: TaskRoomDataBase): TaskDao {
        return demoDatabase.taskDao()
    }

    @Singleton
    @Provides
    fun providesCompletedTaskProductDao(demoDatabase: TaskRoomDataBase): CompletedTaskDao {
        return demoDatabase.completedTaskDao()
    }

    @Singleton
    @Provides
    fun providesLocalTaskRepository(taskDao: TaskDao): TaskLocalRepository =
        TaskLocalRepository(taskDao)

    @Singleton
    @Provides
    fun providesCompletedLocalTaskRepository(taskDao: CompletedTaskDao): CompletedTaskLocalRepository =
        CompletedTaskLocalRepository(taskDao)
}