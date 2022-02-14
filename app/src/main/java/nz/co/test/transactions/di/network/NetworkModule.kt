package nz.co.test.transactions.di.network

import dagger.Module
import dagger.Provides
import nz.co.test.transactions.services.TransactionsService
import retrofit2.Retrofit

@Module
class NetworkModule {
    @Provides
    fun providesTransactionService(retrofit: Retrofit): TransactionsService =
        retrofit.create(TransactionsService::class.java)
}