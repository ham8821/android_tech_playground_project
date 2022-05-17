package nz.co.test.transactions.ui.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.co.test.transactions.App
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.ui.AppTheme
import nz.co.test.transactions.ui.InputField
import nz.co.test.transactions.ui.states.TaskDetailState
import nz.co.test.transactions.ui.states.TaskViewHolderState


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskDetailScreenView(
    navController: NavController,
    userId: String?,
    application: App,
    viewModel: TaskViewModel,
    navigationIcon: @Composable() (() -> Unit)?
) {
    val state by viewModel.detailState.collectAsState()
    viewModel.getTask(userId!!)
    AppTheme(useDarkTheme = application.isDark.value) {

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
                        IconButton(onClick = {
                            viewModel.openEditMode()
                        }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit Task")
                        }
                    }
                )
            }
        ) {
            TaskDetailView(state, navController, Modifier.fillMaxSize())
        }
    }
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
fun TaskDetailDataTextView(state: TaskViewHolderState, isEnabled: Boolean = false, modifier: Modifier) {
    Column(modifier = Modifier.padding(16.dp)) {
        InputField(
            state.taskName,
            isEnabled,
            "Task Name",
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        )
        InputField(
            state.date,
            isEnabled,
            "Date",
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        )
        InputField(
            state.taskIdentifier,
            isEnabled,
            "ID",
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(8.dp))
        InputField(
            state.taskDescription,
            isEnabled,
            "Description",
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        )
    }
}
