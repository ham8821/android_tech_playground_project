package nz.co.test.transactions

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.infrastructure.repository.TaskLocalRepository
import nz.co.test.transactions.infrastructure.repository.TaskRepository
import nz.co.test.transactions.ui.states.TaskListViewState
import nz.co.test.transactions.ui.utils.hasQuery
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import nz.co.test.transactions.ui.states.TaskDetailState
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

    private val _state: MutableStateFlow<TaskListViewState> = MutableStateFlow(
        TaskListViewState.Loading
    )
    val state: StateFlow<TaskListViewState> = _state

    private val _detailState: MutableStateFlow<TaskDetailState> = MutableStateFlow(
        TaskDetailState.Loading
    )
    val detailState: StateFlow<TaskDetailState> = _detailState

    init {
        viewModelScope.launch {
            val tasks: List<Task> = taskLocalRepository.allTask()
            Log.d("RESPONSE!!!", tasks.toString())
            val taskList: ArrayList<TaskViewHolderState> = arrayListOf()
            tasks.map {
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

    fun getTask(userId: String) {
        viewModelScope.launch {
            val task: Task? = taskLocalRepository.getTask(userId.toInt())
            Log.d("RESPONSE DETAIL", task.toString())
            if (task != null) {
                val taskState =
                    TaskViewHolderState(task.title, task.description, task.date, task.id.toString())
                _detailState.value = TaskDetailState.Loaded(taskState)
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            try {
                taskLocalRepository.addTask(task)
            } catch (e: Throwable) {
                println("DAO DEBUG LOG$e.message")
            }
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

