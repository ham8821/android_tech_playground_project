package nz.co.test.transactions.di.modules

import android.app.Application
import android.content.Context
import javax.inject.Singleton

import androidx.room.Room
import com.example.android.roomwordssample.TransactionRoomDatabase
import dagger.Module
import dagger.Provides
import nz.co.test.transactions.infrastructure.TaskRoomDataBase
import nz.co.test.transactions.infrastructure.dao.TaskDao
import nz.co.test.transactions.infrastructure.dao.TransactionDao
import nz.co.test.transactions.infrastructure.repository.TaskLocalRepository
import nz.co.test.transactions.infrastructure.repository.TransactionsLocalRepository


@Module
class RoomModule() {
    //    private val demoDatabase: TransactionRoomDatabase
    @Singleton
    @Provides
    fun providesRoomDatabase(mApplication: Application): TransactionRoomDatabase {
        return Room.databaseBuilder(
            mApplication,
            TransactionRoomDatabase::class.java,
            "transaction_database"
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun providesTaskRoomDatabase(mApplication: Application): TaskRoomDataBase {
        return Room.databaseBuilder(mApplication, TaskRoomDataBase::class.java, "task_database")
            .allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun providesProductDao(demoDatabase: TransactionRoomDatabase): TransactionDao {
        return demoDatabase.transactionDao()
    }

    @Singleton
    @Provides
    fun providesTaskProductDao(demoDatabase: TaskRoomDataBase): TaskDao {
        return demoDatabase.taskDao()
    }

    @Singleton
    @Provides
    fun providesLocalRepository(transactionDao: TransactionDao): TransactionsLocalRepository =
        TransactionsLocalRepository(transactionDao)

    @Singleton
    @Provides
    fun providesLocalTaskRepository(taskDao: TaskDao): TaskLocalRepository =
        TaskLocalRepository(taskDao)
}