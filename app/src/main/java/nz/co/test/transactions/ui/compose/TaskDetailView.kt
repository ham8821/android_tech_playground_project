package nz.co.test.transactions.ui.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import nz.co.test.transactions.App
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.ui.AppTheme
import nz.co.test.transactions.ui.states.TaskDetailState


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskDetailScreenView(
    navController: NavController,
    userId: String?,
    application: App,
    viewmodel: TaskViewModel,
    navigationIcon: @Composable() (() -> Unit)?
) {
    val state by viewmodel.detailState.collectAsState()
    viewmodel.getTask(userId!!)
    AppTheme(useDarkTheme = application.isDark.value) {

        Scaffold(
            topBar = {
                when (state) {
                    is TaskDetailState.Loading -> TopAppBar(
                        title = { Text("Loading Detail..") },
                        navigationIcon = navigationIcon,
                        backgroundColor = MaterialTheme.colors.background
                    )
                    is TaskDetailState.Loaded ->
                        TopAppBar(
                            title = { NavTitleView(state as TaskDetailState.Loaded) },
                            navigationIcon = navigationIcon,
                            backgroundColor = MaterialTheme.colors.background,
                            actions = {
                                IconButton(onClick = {
                                    viewmodel.removeTask(
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
                                    // Edit mode open
                                }) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Edit Task")
                                }
                            },
                        )
                }
            }
        ) {
            TaskDetailView(state, navController, Modifier.fillMaxSize())
        }
    }
}

@Composable
fun NavTitleView(state: TaskDetailState.Loaded) {
    Text(text = state.data.taskName)
}

@Composable
fun TaskDetailView(state: TaskDetailState, navController: NavController, modifier: Modifier) {
    when (state) {
        is TaskDetailState.Loading -> CircularProgressIndicator()
        is TaskDetailState.Loaded ->
            TaskDetailDataTextView(state, modifier)
    }

}

@Composable
fun TaskDetailDataTextView(state: TaskDetailState.Loaded, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = state.data.taskIdentifier)
        Text(text = state.data.taskName)
        Text(text = state.data.taskDescription)
        Text(text = state.data.date)
    }
}
