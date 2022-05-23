package nz.co.test.transactions.ui.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.co.test.transactions.App
import nz.co.test.transactions.ui.TaskViewModel
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.ui.AppTheme
import nz.co.test.transactions.ui.states.TaskDetailState
import nz.co.test.transactions.ui.states.TaskViewHolderState
import nz.co.test.transactions.ui.utils.Utility

lateinit var taskName: MutableState<String>
lateinit var taskDescription: MutableState<String>

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskDetailScreenView(
    navController: NavController,
    userId: String?,
    application: App,
    viewModel: TaskViewModel,
    navigationIcon: @Composable() (() -> Unit)?,
) {
    val context = LocalContext.current
    val state by viewModel.detailState.collectAsState()
    viewModel.getTask(userId!!)
    AppTheme(useDarkTheme = application.isDark.value) {
        val coroutineScope = rememberCoroutineScope()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "")
                    },
                    navigationIcon = navigationIcon,
                    backgroundColor = MaterialTheme.colors.background,
                    actions = {
                        IconButton(onClick = {
                            viewModel.removeTask(
                                Task(
                                    (state as TaskDetailState.Loaded).data.taskIdentifier.toInt(),
                                    (state as TaskDetailState.Loaded).data.date,
                                    (state as TaskDetailState.Loaded).data.taskName,
                                    (state as TaskDetailState.Loaded).data.taskDescription
                                )
                            )
                            navController.navigateUp()
                        }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Edit Task")
                        }

                        if (state is TaskDetailState.Loaded) {
                            if ((state as TaskDetailState.Loaded).isEditMode) {
                                IconButton(onClick = {
                                    val isValidate = validateInputDetail()
                                    if (isValidate) {
                                        if (taskName.value != (state as TaskDetailState.Loaded).data.taskName && taskName.value == (state as TaskDetailState.Loaded).data.taskDescription) {
                                            viewModel.updateTask(
                                                Task(
                                                    (state as TaskDetailState.Loaded).data.taskIdentifier.toInt(),
                                                    (state as TaskDetailState.Loaded).data.date,
                                                    taskName.value,
                                                    taskDescription.value
                                                )
                                            )
                                            Utility.makeToast(context, "Task updated.")
                                        }
                                        viewModel.closeEditMode()
                                    } else {
                                        Utility.makeToast(context, "fields can't be empty.")
                                    }
                                }) {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = "Close Edit Task Detail"
                                    )
                                }
                            } else {
                                IconButton(onClick = {
                                    viewModel.openEditMode()
                                }) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Edit Task Detail")
                                }
                            }
                        }
                    }
                )
            }
        ) {
            TaskDetailView(state, navController, Modifier.fillMaxSize())
        }
    }
}

fun validateInputDetail(): Boolean {
    return !(taskName.value.isEmpty() || taskDescription.value.isEmpty())
}

@Composable
fun TaskDetailView(state: TaskDetailState, navController: NavController, modifier: Modifier) {
    when (state) {
        is TaskDetailState.Loading -> CircularProgressIndicator()
        is TaskDetailState.Loaded ->
            TaskDetailDataTextView(state.data, state.isEditMode, modifier)
    }

}

@Composable
fun TaskDetailDataTextView(
    state: TaskViewHolderState,
    isEnabled: Boolean = false,
    modifier: Modifier,
) {
    taskName = remember { mutableStateOf(state.taskName) }
    taskDescription = remember { mutableStateOf(state.taskDescription) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = taskName.value,
            onValueChange = { taskName.value = it },
            enabled = isEnabled,
            label = { Text(text = "Task Name") },
            placeholder = { Text(text = state.taskName) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        OutlinedTextField(
            value = state.date,
            onValueChange = { },
            enabled = false,
            label = { Text(text = "Date") },
            placeholder = { Text(text = state.date) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        OutlinedTextField(
            value = state.taskIdentifier,
            onValueChange = { },
            enabled = false,
            label = { Text(text = "ID") },
            placeholder = { Text(text = state.taskIdentifier) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = taskDescription.value,
            onValueChange = { taskDescription.value = it },
            enabled = isEnabled,
            label = { Text(text = "Description") },
            placeholder = { Text(text = state.taskDescription) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )
    }
}
