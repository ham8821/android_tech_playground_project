package nz.co.test.transactions.ui

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
import nz.co.test.transactions.ui.states.CompletedTaskListViewState
import nz.co.test.transactions.ui.states.CompletedTaskViewHolderState
import javax.inject.Inject

@HiltViewModel
class CompletedTaskViewModel @Inject constructor(
    private val taskLocalRepository: TaskLocalRepository,
    private val completedTaskLocalRepository: CompletedTaskLocalRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<CompletedTaskListViewState> = MutableStateFlow(
        CompletedTaskListViewState.Loading
    )
    val state: StateFlow<CompletedTaskListViewState> = _state

    init {
        viewModelScope.launch {
            completedTaskLocalRepository.getTasks().collect { list ->
                if (list.isNotEmpty()) {
                    val taskList: ArrayList<CompletedTaskViewHolderState> = arrayListOf()
                    list.map { task ->
                        taskList.add(
                            CompletedTaskViewHolderState(
                                task.title,
                                task.description,
                                task.date,
                                task.id.toString(),
                                "Confirmed date"
                            )
                        )
                    }
                    _state.value = CompletedTaskListViewState.Loaded(taskList)
                } else {
                    _state.value =
                        CompletedTaskListViewState.Error("You haven't completed any task.")
                }
            }
        }
    }

    fun reAddTaskToList(task: Task){
        viewModelScope.launch {
            try {
                taskLocalRepository.addTask(task)
            } catch (e: Throwable) {
                println("DAO DEBUG LOG$e.message")
            }
        }
    }

    fun removeCompletedTask(task: CompletedTask) {
        viewModelScope.launch {
            try {
                completedTaskLocalRepository.removeTask(task)
            } catch (e: Throwable) {
                println("DAO DEBUG LOG$e.message")
            }
        }
    }
}