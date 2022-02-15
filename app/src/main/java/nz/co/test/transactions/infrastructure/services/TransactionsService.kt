package nz.co.test.transactions.infrastructure.services

import nz.co.test.transactions.infrastructure.model.Transaction
import retrofit2.http.GET

interface TransactionsService {
    @GET("test-data.json")
    suspend fun retrieveTransactions(): List<Transaction>
}

