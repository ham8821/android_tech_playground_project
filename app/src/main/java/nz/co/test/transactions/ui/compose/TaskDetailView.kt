package nz.co.test.transactions.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import dagger.hilt.android.qualifiers.ApplicationContext
import nz.co.test.transactions.App
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.ui.AppTheme
import nz.co.test.transactions.ui.states.TaskDetailState


@Composable
fun TaskDetailScreenView(navController: NavController, userId: String?, application: App, viewmodel: TaskViewModel){
    val state by viewmodel.detailState.collectAsState()
    viewmodel.getTask(userId!!)
    AppTheme(useDarkTheme = application.isDark.value) {
        TaskDetailView(state, navController, Modifier.fillMaxSize())
    }
}

@Composable
fun TaskDetailView(state: TaskDetailState, navController: NavController, modifier: Modifier) {
        when(state){
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
