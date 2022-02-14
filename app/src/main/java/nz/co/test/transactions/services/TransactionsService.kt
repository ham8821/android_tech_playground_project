package nz.co.test.transactions.services

import retrofit2.http.GET

interface TransactionsService {
    @GET("transactions")
    suspend fun retrieveTransactions(): Array<Transaction>
}

