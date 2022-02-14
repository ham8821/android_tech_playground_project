package nz.co.test.transactions.infrastructure.interactor

import nz.co.test.transactions.infrastructure.model.Transaction
import nz.co.test.transactions.infrastructure.repository.TransactionsRepository

class TransactionsInteractorImpl(private val transactionsRepository: TransactionsRepository): TransactionsInteractor {
    override suspend fun retrieveTransactions() {
        transactionsRepository.retrieveTransactions()
    }
}