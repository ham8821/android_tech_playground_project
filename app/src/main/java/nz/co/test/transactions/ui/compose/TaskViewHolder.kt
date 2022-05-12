package nz.co.test.transactions.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import nz.co.test.transactions.ui.states.TaskViewHolderState

@Composable
fun TaskViewHolder(
    navController: NavController,
    state: TaskViewHolderState,
    modifier: Modifier
) {
    Column(modifier = Modifier
        .clickable {
            navController.navigate("taskDetail/" + state.taskIdentifier)
        }
        .padding(12.dp)) {
        TaskTitleView(title = state.taskName, modifier = modifier)
        TaskDescriptionView(description = state.taskDescription, modifier = modifier)
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

@Composable
fun DateTextView(
    date: String,
    modifier: Modifier
) {
    Text(
        text = date,
        fontSize = 12.sp,
        modifier = modifier
    )
}

@Composable
fun TaskIdentifierTextView(
    identifier: String,
    modifier: Modifier
) {
    Text(
        text = identifier,
        fontSize = 12.sp,
        modifier = modifier
    )
}
//
//@Preview
//@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
//    showBackground = true
//)
//@Composable
//fun ArticleViewHolderPreview() {
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
