package nz.co.test.transactions.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.ui.states.TaskViewHolderState

@Composable
fun TaskViewHolder(
    navController: NavController,
    state: TaskViewHolderState,
    viewModel: TaskViewModel,
    modifier: Modifier
) {

    Box(modifier = Modifier.fillMaxWidth()) {

        Column(modifier = Modifier.clickable {
            navController.navigate("taskDetail/" + state.taskIdentifier)
        }) {
            TaskTitleView(title = state.taskName, modifier = modifier)
            TaskDescriptionView(description = state.taskDescription, modifier = modifier)
        }

        IconButton(onClick = {
            viewModel.removeTask(
                Task(
                    state.taskIdentifier.toInt(),
                    state.date,
                    state.taskName,
                    state.taskDescription
                )
            )
            navController.navigate("taskList")
        }, modifier = Modifier.align(Alignment.TopEnd)) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "close bottomsheet",
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Composable
fun TaskTitleView(
    title: String,
    modifier: Modifier
) {
    Text(
        text = title,
        modifier = modifier,
        fontSize = 16.sp
    )
}

@Composable
fun TaskDescriptionView(
    description: String,
    modifier: Modifier
) {
    Text(
        text = description,
        fontSize = 12.sp,
        modifier = modifier

    )
}

//@Preview
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true
//)
//@Composable
//private fun ArticleViewHolderPreview() {
//    AppTheme {
//        TaskViewHolder(
//            navController = NavController(this),
//            state = TaskViewHolderState(
//                taskName = "Task Title",
//                taskDescription = "Task Description",
//                date = "Task Date",
//                taskIdentifier = "Identifier"
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//                .padding(
//                    all = dimensionResource(
//                        id = R.dimen.default_margin
//                    )
//                )
//        )
//    }
//}
