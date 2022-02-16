package nz.co.test.transactions.infrastructure.repository

import android.view.SurfaceControl
import nz.co.test.transactions.infrastructure.model.Transaction
import nz.co.test.transactions.infrastructure.services.TransactionsService
import javax.inject.Inject

class TransactionsRepository @Inject constructor(private val transactionsService: TransactionsService) {
    suspend fun retrieveTransactions() = transactionsService.retrieveTransactions().let { list ->
        list.sortedByDescending { it.transactionDate }
    }

//    suspend fun retrieveTransactions() = Array<Transaction>()
}