package nz.co.test.transactions.infrastructure.repository

import nz.co.test.transactions.infrastructure.model.Transaction
import nz.co.test.transactions.infrastructure.services.TransactionsService

class TransactionsRepositoryImpl(private val transactionsService: TransactionsService): TransactionsRepository {
    override suspend fun retrieveTransactions() {
        transactionsService.retrieveTransactions()
    }
}