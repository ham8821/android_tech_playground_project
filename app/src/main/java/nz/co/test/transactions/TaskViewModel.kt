package nz.co.test.transactions

import androidx.lifecycle.*
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.infrastructure.repository.TaskLocalRepository
import nz.co.test.transactions.infrastructure.repository.TaskRepository
import nz.co.test.transactions.ui.utils.hasQuery
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskLocalRepository: TaskLocalRepository
) :
    ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showNoTransactonFoundView: MutableLiveData<Boolean> = MutableLiveData()
    val showListScreen: MutableLiveData<Boolean> = MutableLiveData()
    val allTasks: LiveData<List<Task>> = taskLocalRepository.allTask
        .onStart { isLoading.value = true }
        .asLiveData()

    fun addTask(task: Task) {
        viewModelScope.launch {
            taskLocalRepository.addTask(task)
        }
    }

    fun removeTask(task: Task) {
        viewModelScope.launch {
            taskLocalRepository.removeTask(task)
        }
        showListScreen.value = true
    }

    fun removeAllTasks() {
        viewModelScope.launch {
            taskLocalRepository.removeAllTasks()
        }
    }

    fun searchTask(task: List<Task>, savedQuery: String): Array<Task> {
        return task // for unit tests that fill partial data
            .filter {
                it.title hasQuery savedQuery ||
                        it.id.toString() hasQuery savedQuery
                //                        it.transactionDetail?.mOtherAccountStatementDetail hasQuery queryString ||
                //                        getTargetAmountString(it.amount!! > 0, it.formattedAmount, queryString) hasQuery getSearchAmountString(queryString)
            }.toTypedArray()
    }

}

