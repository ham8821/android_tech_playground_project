package nz.co.test.transactions.di.modules

import dagger.Module
import dagger.Provides
import nz.co.test.transactions.infrastructure.repository.TransactionsRepository
import nz.co.test.transactions.infrastructure.services.TransactionsService
import javax.inject.Singleton

@Module
class DataModule {
    @Singleton
    @Provides
    fun providesRepository(transactionService: TransactionsService): TransactionsRepository =
        TransactionsRepository(transactionService)
}