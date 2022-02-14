package nz.co.test.transactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import nz.co.test.transactions.infrastructure.model.Transaction
import nz.co.test.transactions.infrastructure.repository.TransactionsRepository
import javax.inject.Inject

class TransactionListViewModel @Inject constructor(private val transactionRepository: TransactionsRepository): ViewModel() {

    private val viewModelJob = SupervisorJob()

    val sortedTransactionList = MutableLiveData<List<Transaction>>()
    /**
     * This is the main scope for all coroutines launched by MainViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun retrieveTransactions(){
        uiScope.launch {
            val transactions = transactionRepository.retrieveTransactions()
            sortedTransactionList.value = transactions.toList()
        }
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
