package nz.co.test.transactions

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nz.co.test.transactions.infrastructure.model.Transaction
import nz.co.test.transactions.infrastructure.repository.TransactionsLocalRepository
import nz.co.test.transactions.infrastructure.repository.TransactionsRepository
import javax.inject.Inject

class TransactionListViewModel @Inject constructor(
    private val transactionRepository: TransactionsRepository,
    private val transactionsLocalRepository: TransactionsLocalRepository
) :
    ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showNoTransactonFoundView: MutableLiveData<Boolean> = MutableLiveData()
    val showListScreen: MutableLiveData<Boolean> = MutableLiveData()
    val allTransactions: LiveData<List<Transaction>> = transactionsLocalRepository.allTransactions
        .onStart { isLoading.value = true }
        .asLiveData()

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionsLocalRepository.addTransaction(transaction)
        }
    }

    fun removeTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionsLocalRepository.removeTransaction(transaction)
        }
        showListScreen.value = true
    }

    fun removeAllTransaction() {
        viewModelScope.launch {
            transactionsLocalRepository.removeAllTransactions()
        }
    }
}

