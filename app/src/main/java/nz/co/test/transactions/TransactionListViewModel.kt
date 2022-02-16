package nz.co.test.transactions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import nz.co.test.transactions.infrastructure.model.Transaction
import nz.co.test.transactions.infrastructure.repository.TransactionsRepository
import javax.inject.Inject

class TransactionListViewModel @Inject constructor(private val transactionRepository: TransactionsRepository) :
    ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showNoTransactonFoundView: MutableLiveData<Boolean> = MutableLiveData()
    val showTransactions: MutableLiveData<List<Transaction>> = MutableLiveData()

    fun retrieveTransactions() {
        viewModelScope.launch {
            handleStatus(Resource.loading(data = null))
            try {
                handleStatus(Resource.success(data = transactionRepository.retrieveTransactions()))
            } catch (exception: Exception) {
                handleStatus(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }

    private fun handleStatus(state: Resource<List<Transaction>>) {
        when (state.status) {
            Status.LOADING -> isLoading.value = true
            Status.ERROR -> {
                isLoading.value = false
                showNoTransactonFoundView.value = true
                showTransactions.value = null
            }
            Status.SUCCESS -> {
                isLoading.value = false
                showTransactions.value = state.data
            }
        }
    }
}

