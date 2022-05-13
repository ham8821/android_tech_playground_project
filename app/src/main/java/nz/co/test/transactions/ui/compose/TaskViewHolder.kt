package nz.co.test.transactions.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.RadioButton
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.ui.states.TaskViewHolderState
import nz.co.test.transactions.ui.utils.Utility.makeToast

@Composable
fun TaskViewHolder(
    navController: NavController,
    state: TaskViewHolderState,
    viewModel: TaskViewModel,
    modifier: Modifier
) {
val context = LocalContext.current
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
                makeToast(context, "item clicked")
            }
        )

        Column(modifier = Modifier.fillMaxHeight().weight(1f), verticalArrangement = Arrangement.Top) {
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
        }, modifier = Modifier
        ) {
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
