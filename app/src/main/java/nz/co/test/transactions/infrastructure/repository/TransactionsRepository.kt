package nz.co.test.transactions.infrastructure.repository

import nz.co.test.transactions.infrastructure.model.Transaction

interface TransactionsRepository {
    suspend fun retrieveTransactions()
}