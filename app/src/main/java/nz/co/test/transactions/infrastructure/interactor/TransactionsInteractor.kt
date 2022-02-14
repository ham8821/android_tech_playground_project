package nz.co.test.transactions.infrastructure.interactor

import nz.co.test.transactions.infrastructure.model.Transaction
import nz.co.test.transactions.infrastructure.repository.TransactionsRepository

interface TransactionsInteractor {
    suspend fun retrieveTransactions()
}