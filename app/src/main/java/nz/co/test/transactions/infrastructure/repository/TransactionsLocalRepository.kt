package nz.co.test.transactions.infrastructure.repository

import nz.co.test.transactions.infrastructure.dao.TransactionDao
import nz.co.test.transactions.infrastructure.model.Transaction
import nz.co.test.transactions.infrastructure.services.TransactionsService
import javax.inject.Inject

class TransactionsLocalRepository @Inject constructor(private val transactionDao: TransactionDao) {
    suspend fun getAllTransactions() = transactionDao.retrieveTransactions()

    suspend fun addTransaction(transaction: Transaction){
        transactionDao.insert(transaction)
    }

    suspend fun removeTransaction(transaction: Transaction){
        transactionDao.deleteTransaction(transaction)
    }

    suspend fun removeAllTransactions() {
        transactionDao.deleteAll()
    }
}