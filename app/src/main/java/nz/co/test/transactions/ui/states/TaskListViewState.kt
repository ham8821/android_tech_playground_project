package nz.co.test.transactions.ui.states

sealed class TaskListViewState {
    object Loading : TaskListViewState()
    data class Loaded(val data: List<TaskViewHolderState>) : TaskListViewState()
    data class Error(val errorMsg: String) : TaskListViewState()
}


sealed class CompletedTaskListViewState {
    object Loading : CompletedTaskListViewState()
    data class Loaded(val data: List<CompletedTaskViewHolderState>) : CompletedTaskListViewState()
    data class Error(val errorMsg: String) : CompletedTaskListViewState()
}
