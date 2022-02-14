package nz.co.test.transactions.infrastructure.services

import nz.co.test.transactions.infrastructure.model.Transaction
import retrofit2.http.GET

interface TransactionsService {
    @GET("transactions")
    suspend fun retrieveTransactions(): Array<Transaction>
}

