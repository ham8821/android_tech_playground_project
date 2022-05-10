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
    ConstraintLayout(modifier = modifier) {
        val (
            taskNameView, taskDescriptionView, dateView, taskIDView
        ) = createRefs()
        val standardMargin = dimensionResource(
            id = R.dimen.default_margin
        )
        TaskTitleView(state.taskName, modifier = Modifier.constrainAs(taskNameView) {
            width = Dimension.wrapContent
            start.linkTo(
                anchor = parent.start,
                margin = standardMargin
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
                    anchor = parent.start,
                    margin = standardMargin
                )
                top.linkTo(taskNameView.bottom)
                end.linkTo(taskIDView.start)
                bottom.linkTo(parent.bottom)
            })
        DateTextView(state.date, modifier = Modifier.constrainAs(dateView) {
            width = Dimension.wrapContent
            start.linkTo(
                anchor = taskNameView.end,
                margin = standardMargin
            )
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(taskIDView.top)
        })
        TaskIdentifierTextView(state.taskIdentifier, modifier = Modifier.constrainAs(taskIDView) {
            width = Dimension.wrapContent
            start.linkTo(
                anchor = taskDescriptionView.end,
                margin = standardMargin
            )
            top.linkTo(dateView.bottom)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
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
        color = MaterialTheme.colors.onPrimary,
        modifier = modifier,
        fontSize = fontDimensionResource(id = R.dimen.font_16)
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
        fontSize = fontDimensionResource(id = R.dimen.font_12),
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
        fontSize = fontDimensionResource(id = R.dimen.font_12),
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
        fontSize = fontDimensionResource(id = R.dimen.font_12),
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
