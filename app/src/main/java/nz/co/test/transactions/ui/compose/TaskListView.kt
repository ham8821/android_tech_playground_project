package nz.co.test.transactions.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import nz.co.test.transactions.R
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.ui.states.TaskListViewState
import nz.co.test.transactions.ui.states.TaskViewHolderState
import nz.co.test.transactions.ui.utils.Utility.makeToast
import androidx.compose.runtime.getValue

@Composable
fun TaskListScreenView(navController: NavController, viewModel: TaskViewModel) {
    val state by viewModel.state.collectAsState()
    TaskListView(
        state = state,
        navController,
        modifier = Modifier
            .fillMaxSize()
    )
}
@Composable
fun TaskListView(state: TaskListViewState, navController: NavController, modifier: Modifier) {
    val defaultMargin = dimensionResource(
        id = R.dimen.default_margin
    )
    ConstraintLayout(modifier = modifier) {
        val (
            LoadingView, LoadedView, ErrorView
        ) = createRefs()
        when (state) {
            is TaskListViewState.Loading -> CircularProgressIndicator(
                modifier = Modifier.constrainAs(LoadingView) {
                    start.linkTo(
                        parent.start,
                        margin = defaultMargin
                    )
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )

            is TaskListViewState.Loaded -> TaskListRecyclerView(
                data = state.data,
                navController = navController,
                modifier = Modifier.constrainAs(LoadedView) {
                    start.linkTo(
                        parent.start,
                        margin = defaultMargin
                    )
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
            )
            is TaskListViewState.Error -> TaskErrorView(
                data = state.errorMsg,
                navController = navController,
                modifier = Modifier.constrainAs(ErrorView) {
                    start.linkTo(
                        parent.start,
                        margin = defaultMargin
                    )
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
    }
}

@Composable
fun TaskErrorView(data: String, navController: NavController, modifier: Modifier) {
    Text(text = data, color = Color.Red, modifier = modifier)
}

@Composable
fun TaskListRecyclerView(
    data: List<TaskViewHolderState>,
    navController: NavController,
    modifier: Modifier
) {
    val context = LocalContext.current
    LazyColumn(modifier = modifier) {
        items(data) { taskInfo ->
            TaskViewHolder(
                state = taskInfo,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { makeToast(context = context, "item Clicked!!") })
            Divider(
                color = Color.Gray,
                thickness = dimensionResource(
                    id = R.dimen.divider_size
                ),
                modifier = Modifier
                    .padding(
                        bottom = dimensionResource(
                            id = R.dimen.divider_size
                        )
                    )
            )
        }

    }
}

