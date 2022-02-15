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
class TransactionListViewModel @Inject constructor(private val transactionRepository: TransactionsRepository): ViewModel() {

    fun retrieveTransactions() =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = transactionRepository.retrieveTransactions()))
            } catch (exception: Exception) {
                emit(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Error Occurred!"
                    )
                )
            }
        }

}

