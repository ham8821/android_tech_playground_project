package nz.co.test.transactions.ui.states

sealed class TaskListViewState {
    object Loading : TaskListViewState()
    data class Loaded(val data: List<TaskViewHolderState>) : TaskListViewState()
    data class Error(val errorMsg: String) : TaskListViewState()
}
