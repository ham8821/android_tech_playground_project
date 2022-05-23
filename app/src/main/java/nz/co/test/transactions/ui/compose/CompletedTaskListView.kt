package nz.co.test.transactions.ui.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import nz.co.test.transactions.App
import nz.co.test.transactions.R
import nz.co.test.transactions.ui.AppTheme
import nz.co.test.transactions.ui.CompletedTaskViewModel
import nz.co.test.transactions.ui.states.CompletedTaskListViewState
import nz.co.test.transactions.ui.states.CompletedTaskViewHolderState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun CompletedTaskListScreenView(
    navController: NavController,
    application: App,
    viewModel: CompletedTaskViewModel,
) {
    val state by viewModel.state.collectAsState()
    AppTheme(useDarkTheme = application.isDark.value) {
        val systemUiController = rememberSystemUiController()
        val darkIcons = !application.isDark.value
        val statusBarColor = if (darkIcons) Color.White else Color.Black
        SideEffect {
            systemUiController.setSystemBarsColor(statusBarColor, darkIcons = darkIcons)
        }
        CompletedTaskListView(state, viewModel, navController, modifier = Modifier.fillMaxWidth())
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CompletedTaskListView(
    state: CompletedTaskListViewState,
    viewModel: CompletedTaskViewModel,
    navController: NavController,
    modifier: Modifier,
) {
    val defaultMargin = dimensionResource(
        id = R.dimen.default_margin
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Completed tasks",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Justify)
                },
                elevation = AppBarDefaults.TopAppBarElevation,
                backgroundColor = MaterialTheme.colors.background
            )
        }
    ) {
        ConstraintLayout(modifier = modifier) {
            val (
                LoadingView, LoadedView, ErrorView,
            ) = createRefs()
            when (state) {
                is CompletedTaskListViewState.Loading -> CircularProgressIndicator(
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

                is CompletedTaskListViewState.Loaded -> {
                    CompletedTaskListRecyclerView(
                        data = state.data,
                        navController = navController,
                        viewModel = viewModel,
                        modifier = Modifier.constrainAs(LoadedView) {
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
                is CompletedTaskListViewState.Error -> TaskErrorView(
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
}

@Composable
fun CompletedTaskListRecyclerView(
    data: List<CompletedTaskViewHolderState>,
    navController: NavController,
    viewModel: CompletedTaskViewModel,
    modifier: Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(data) { taskInfo ->
            CompletedTaskViewHolder(
                navController = navController,
                state = taskInfo,
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            Divider(
                color = MaterialTheme.colors.secondaryVariant,
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