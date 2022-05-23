package nz.co.test.transactions.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import nz.co.test.transactions.R
import nz.co.test.transactions.infrastructure.model.CompletedTask
import nz.co.test.transactions.ui.TaskViewModel
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.ui.states.TaskViewHolderState
import nz.co.test.transactions.ui.utils.Utility

@Composable
fun TaskViewHolder(
    navController: NavController,
    state: TaskViewHolderState,
    viewModel: TaskViewModel,
    modifier: Modifier
) {
    var openDialog by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clickable {
                navController.navigate("taskDetail/" + state.taskIdentifier)
            }
            .padding(8.dp)
    ) {

        RadioButton(
            selected = false, modifier = Modifier,
            onClick = {
                openDialog = true
            }
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f), verticalArrangement = Arrangement.Top
        ) {
            TaskTitleView(
                title = state.taskName,
                modifier = Modifier
                    .wrapContentHeight()
            )
            TaskDescriptionView(
                description = state.taskDescription,
                modifier = Modifier
                    .wrapContentHeight()
            )
        }

        IconButton(
            onClick = {
                viewModel.removeTask(
                    Task(
                        state.taskIdentifier.toInt(),
                        state.date,
                        state.taskName,
                        state.taskDescription
                    )
                )
            }, modifier = Modifier
        ) {
            Icon(
                Icons.Filled.Delete,
                contentDescription = "close bottomsheet",
                tint = MaterialTheme.colors.onBackground
            )
        }
    }

    if (openDialog) {
        AlertDialog(
            modifier = Modifier.padding(20.dp),
            onDismissRequest = { openDialog = false },
            title = {
                Text(
                    text = stringResource(id = R.string.complete_task),
                    style = MaterialTheme.typography.h6
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.complete_task_msg),
                    style = MaterialTheme.typography.body1
                )
            },
            confirmButton = {
                Text(
                    text = stringResource(id = R.string.complete),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .padding(15.dp)
                        .clickable {
                            openDialog = false
                            viewModel.removeTask(
                                Task(
                                    state.taskIdentifier.toInt(),
                                    state.date,
                                    state.taskName,
                                    state.taskDescription
                                )
                            )
                            viewModel.addCompletedTask(CompletedTask(
                                state.taskIdentifier.toInt(),
                                state.date,
                                Utility.getFormattedCurrentDate(),
                                state.taskName,
                                state.taskDescription
                            ))
                        }
                )
            }
        )
    }
}


@Composable
fun TaskTitleView(
    title: String,
    modifier: Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.body1,
        modifier = modifier,
        fontSize = 14.sp
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
