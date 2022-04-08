package nz.co.test.transactions.di.modules

import dagger.Module
import dagger.Provides
import nz.co.test.transactions.infrastructure.dao.TransactionDao
import nz.co.test.transactions.infrastructure.repository.TaskRepository
import nz.co.test.transactions.infrastructure.repository.TransactionsLocalRepository
import nz.co.test.transactions.infrastructure.repository.TransactionsRepository
import nz.co.test.transactions.infrastructure.services.TaskService
import nz.co.test.transactions.infrastructure.services.TransactionsService
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun providesRepository(transactionService: TransactionsService): TransactionsRepository =
        TransactionsRepository(transactionService)

    @Singleton
    @Provides
    fun providesTaskRepository(taskService: TaskService): TaskRepository =
        TaskRepository(taskService)
}