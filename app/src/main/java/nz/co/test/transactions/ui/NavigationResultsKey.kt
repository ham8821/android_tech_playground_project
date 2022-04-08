package nz.co.kiwibank.mobile.ui.utils.ui

sealed class NavigationResultsKey(val name: String) {

    sealed class TaskResult(key: String) : NavigationResultsKey(key) {
        object TaskUpdateAction : TaskResult("task_update_needed")
        object CardPinUpdated : TaskResult("card-pin-updated")
    }
}