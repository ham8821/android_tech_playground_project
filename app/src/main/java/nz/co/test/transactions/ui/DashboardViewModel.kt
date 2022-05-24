package nz.co.test.transactions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nz.co.test.transactions.infrastructure.repository.CompletedTaskLocalRepository
import nz.co.test.transactions.infrastructure.repository.TaskLocalRepository
import nz.co.test.transactions.infrastructure.repository.TaskRepository
import nz.co.test.transactions.ui.states.TaskListViewState
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val taskLocalRepository: TaskLocalRepository,
    private val completedTaskLocalRepository: CompletedTaskLocalRepository,
) : ViewModel() {

    val taskCount = MutableLiveData<Int>()
    val completedTaskCount = MutableLiveData<Int>()

    init {
        taskCount.value = 0
        completedTaskCount.value = 0

        viewModelScope.launch {
            val counts = taskLocalRepository.getNumTasks()
            taskCount.value = counts

            val completedCounts = completedTaskLocalRepository.getNumCompletedTasks()
            completedTaskCount.value =  completedCounts
        }
    }

}