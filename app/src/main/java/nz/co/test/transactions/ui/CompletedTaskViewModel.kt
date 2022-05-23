package nz.co.test.transactions.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nz.co.test.transactions.infrastructure.repository.CompletedTaskLocalRepository
import nz.co.test.transactions.ui.states.TaskListViewState
import nz.co.test.transactions.ui.states.TaskViewHolderState
import javax.inject.Inject

@HiltViewModel
class CompletedTaskViewModel @Inject constructor(
    private val completedTaskLocalRepository: CompletedTaskLocalRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<TaskListViewState> = MutableStateFlow(
        TaskListViewState.Loading
    )
    val state: StateFlow<TaskListViewState> = _state

    init {
        viewModelScope.launch {
            completedTaskLocalRepository.getTasks().collect { list ->
                if (list.isNotEmpty()) {
                    val taskList: ArrayList<TaskViewHolderState> = arrayListOf()
                    list.map { task ->
                        taskList.add(
                            TaskViewHolderState(
                                task.title,
                                task.description,
                                task.date,
                                task.id.toString(),
                                task.completedDate
                            )
                        )
                    }
                    _state.value = TaskListViewState.Loaded(taskList)
                } else {
                    _state.value =
                        TaskListViewState.Error("You haven't completed any task.")
                }
            }
        }
    }
}