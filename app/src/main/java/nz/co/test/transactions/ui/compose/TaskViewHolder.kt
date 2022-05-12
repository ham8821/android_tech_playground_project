package nz.co.test.transactions.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import nz.co.test.transactions.R
import nz.co.test.transactions.ui.fontDimensionResource
import nz.co.test.transactions.ui.playgroundTheme
import nz.co.test.transactions.ui.states.TaskViewHolderState

@Composable
fun TaskViewHolder(
    state: TaskViewHolderState,
    modifier: Modifier
) {
    val standardMargin = dimensionResource(
        id = R.dimen.default_margin
    )
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth().padding(standardMargin)) {
        val (
            taskNameView, taskDescriptionView, dateView
        ) = createRefs()
        TaskTitleView(state.taskName, modifier = Modifier.constrainAs(taskNameView) {
            width = Dimension.fillToConstraints
            start.linkTo(
                margin = standardMargin,
                anchor = parent.start
            )
            top.linkTo(parent.top)
            end.linkTo(dateView.start)
            bottom.linkTo(taskDescriptionView.top)
        })
        TaskDescriptionView(
            state.taskDescription,
            modifier = Modifier.constrainAs(taskDescriptionView) {
                width = Dimension.wrapContent
                start.linkTo(
                    margin = standardMargin,
                    anchor = parent.start
                )
                top.linkTo(taskNameView.bottom)
                bottom.linkTo(parent.bottom)
            })
        DateTextView(state.date, modifier = Modifier.constrainAs(dateView) {
            width = Dimension.wrapContent
            top.linkTo(parent.top)
            end.linkTo(margin = standardMargin, anchor = parent.end)
        })
    }

}

@Composable
fun TaskTitleView(
    title: String,
    modifier: Modifier
) {
    Text(
        text = title,
        color = MaterialTheme.colors.onSecondary,
        modifier = modifier,
        fontSize = 12.sp
    )
}

@Composable
fun TaskDescriptionView(
    description: String,
    modifier: Modifier
) {
    Text(
        text = description,
        color = MaterialTheme.colors.onSecondary,
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
        color = MaterialTheme.colors.onSecondary,
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
        color = MaterialTheme.colors.onSecondary,
        fontSize = 12.sp,
        modifier = modifier
    )
}

@Preview
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun ArticleViewHolderPreview() {
    playgroundTheme {
        TaskViewHolder(
            state = TaskViewHolderState(
                taskName = "Task Title",
                taskDescription = "Task Description",
                date = "Task Date",
                taskIdentifier = "Identifier"
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    all = dimensionResource(
                        id = R.dimen.default_margin
                    )
                )
        )
    }
}
