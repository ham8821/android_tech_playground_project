package nz.co.test.transactions

import android.util.Log
import androidx.compose.ui.res.stringResource
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
    private lateinit var detail: TaskViewHolderState

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
            taskLocalRepository.getTasks().collect { list ->
                if (list.isNotEmpty()) {
                    val taskList: ArrayList<TaskViewHolderState> = arrayListOf()
                    list.map { task ->
                        taskList.add(
                            TaskViewHolderState(
                                task.title,
                                task.description,
                                task.date,
                                task.id.toString()
                            )
                        )
                    }
                    _state.value = TaskListViewState.Loaded(taskList)
                } else {
                    _state.value =
                        TaskListViewState.Error("You have no task. \nLet\'s add some tasks by clicking the plus button below!")
                }
            }
        }
    }

    fun getTask(userId: String) {
        viewModelScope.launch {
            val task: Task? = taskLocalRepository.getTask(userId.toInt())
            Log.d("RESPONSE DETAIL", task.toString())
            if (task != null) {
                detail =
                    TaskViewHolderState(task.title, task.description, task.date, task.id.toString())
                _detailState.value = TaskDetailState.Loaded(detail, false)
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

    fun openEditMode() {
        _detailState.value = TaskDetailState.Loaded(detail, true)
    }

}

