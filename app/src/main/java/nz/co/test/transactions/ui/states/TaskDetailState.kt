package nz.co.test.transactions.ui.states
sealed class TaskDetailState {
    object Loading : TaskDetailState()
    data class Loaded(val data: TaskViewHolderState) : TaskDetailState()
    data class Error(val errorMsg: String) : TaskDetailState()
}