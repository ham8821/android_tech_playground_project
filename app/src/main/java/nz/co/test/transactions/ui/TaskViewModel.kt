package nz.co.test.transactions.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nz.co.test.transactions.infrastructure.model.CompletedTask
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.infrastructure.repository.CompletedTaskLocalRepository
import nz.co.test.transactions.infrastructure.repository.TaskLocalRepository
import nz.co.test.transactions.infrastructure.repository.TaskRepository
import nz.co.test.transactions.ui.states.TaskDetailState
import nz.co.test.transactions.ui.states.TaskListViewState
import nz.co.test.transactions.ui.states.TaskViewHolderState
import nz.co.test.transactions.ui.utils.hasQuery
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskLocalRepository: TaskLocalRepository,
    private val completedTaskLocalRepository: CompletedTaskLocalRepository
) :
    ViewModel() {

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

    fun addCompletedTask(task: CompletedTask){
        viewModelScope.launch {
            try {
                completedTaskLocalRepository.addTask(task)
            } catch (e: Throwable) {
                println("DAO DEBUG LOG$e.message")
            }
        }
    }
    fun removeTask(task: Task) {
        viewModelScope.launch {
            taskLocalRepository.removeTask(task)
        }
    }

    fun removeAllTasks() {
        viewModelScope.launch {
            taskLocalRepository.removeAllTasks()
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            taskLocalRepository.updateTask(task)
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

    fun closeEditMode() {
        _detailState.value = TaskDetailState.Loaded(detail, false)
    }

}

