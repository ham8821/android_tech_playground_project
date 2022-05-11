package nz.co.test.transactions.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import nz.co.test.transactions.R
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.ui.states.TaskListViewState
import nz.co.test.transactions.ui.states.TaskViewHolderState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
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

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun TaskListView(state: TaskListViewState, navController: NavController, modifier: Modifier) {
    val defaultMargin = dimensionResource(
        id = R.dimen.default_margin
    )
    val bottomSheetItems = listOf(
        BottomSheetItem(title = "Folder", icon = Icons.Default.Favorite),
        BottomSheetItem(title = "Upload", icon = Icons.Default.ThumbUp),
        BottomSheetItem(title = "Scan", icon = Icons.Default.Info),
        BottomSheetItem(title = "Google Docs", icon = Icons.Default.List),
        BottomSheetItem(title = "Google Sheets", icon = Icons.Default.Person)
    )

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Column(
                content = {
                    Spacer(modifier = Modifier.padding(16.dp))
                    Text(
                        text = "Create New",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,
                        color = Color.White
                    )
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(3)
                    ) {
                        items(bottomSheetItems.size, itemContent = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp)
                            ) {
                                Spacer(modifier = Modifier.padding(8.dp))
                                Icon(
                                    bottomSheetItems[it].icon,
                                    "",
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text(text = bottomSheetItems[it].title, color = Color.White)
                            }

                        })
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xAA3fa7cc))
                    .padding(16.dp)
            )
        },
        sheetPeekHeight = 0.dp,
        topBar = {}){
        Scaffold(
            floatingActionButton = {
                AddFloatingButton(coroutineScope, bottomSheetScaffoldState, navController, modifier)
            }
        ) {
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
    LazyColumn(modifier = modifier) {
        items(data) { taskInfo ->
            TaskViewHolder(
                state = taskInfo,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("taskDetail/" + taskInfo.taskIdentifier)
                    })
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

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun AddFloatingButton(
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    navController: NavController,
    modifier: Modifier
) {

    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Filled.Add, "") },
        text = { Text("New Task") },
        onClick = {
            coroutineScope.launch {
                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                } else {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        },
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}

data class BottomSheetItem(val title: String, val icon: ImageVector)
