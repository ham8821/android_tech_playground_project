package nz.co.test.transactions

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.infrastructure.repository.TaskLocalRepository
import nz.co.test.transactions.infrastructure.repository.TaskRepository
import nz.co.test.transactions.ui.states.TaskListViewState
import nz.co.test.transactions.ui.utils.hasQuery
import javax.inject.Inject
import androidx.compose.runtime.getValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import nz.co.test.transactions.ui.states.TaskViewHolderState

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskLocalRepository: TaskLocalRepository
) :
    ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showNoTransactonFoundView: MutableLiveData<Boolean> = MutableLiveData()
    val showListScreen: MutableLiveData<Boolean> = MutableLiveData()
//    val allTasks: LiveData<List<Task>> = taskLocalRepository.allTask
//        .onStart { isLoading.value = true }
//        .asLiveData()

    private val _state: MutableStateFlow<TaskListViewState> = MutableStateFlow(
        TaskListViewState.Loading
    )
    val state: StateFlow<TaskListViewState> = _state

    init {
        viewModelScope.launch {
            taskLocalRepository.allTask.onStart {
                _state.value = TaskListViewState.Loading
            }.onEach { responseBody ->
                val taskList: ArrayList<TaskViewHolderState> = arrayListOf()
                responseBody?.map {
                    taskList.add(
                        TaskViewHolderState(
                            it.title,
                            it.description,
                            it.date,
                            it.id.toString()
                        )
                    )
                }
                if (taskList.isEmpty()) {
                    _state.value = TaskListViewState.Error("Something went wrong. ")
                } else {
                    _state.value = TaskListViewState.Loaded(taskList)
                }
            }

        }
    }

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

