package nz.co.test.transactions.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import nz.co.test.transactions.R
import nz.co.test.transactions.infrastructure.model.CompletedTask
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.ui.CompletedTaskViewModel
import nz.co.test.transactions.ui.states.CompletedTaskViewHolderState

@Composable
fun CompletedTaskViewHolder(
    navController: NavController,
    state: CompletedTaskViewHolderState,
    viewModel: CompletedTaskViewModel,
    modifier: Modifier
) {
    val coroutineScope = rememberCoroutineScope()
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
                coroutineScope.launch {
                    viewModel.reAddTaskToList(
                        Task(
                            state.taskIdentifier.toInt(),
                            state.date,
                            state.taskName,
                            state.taskDescription
                        )
                    )
                    viewModel.removeCompletedTask(
                        CompletedTask(
                            state.taskIdentifier.toInt(),
                            state.date,
                            state.completedDate!!,
                            state.taskName,
                            state.taskDescription
                        )
                    )
                }


            }, modifier = Modifier
        ) {
            Icon(
                Icons.Filled.Refresh,
                contentDescription = "re-add task to task list.",
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

                        }
                )
            }
        )
    }
}
