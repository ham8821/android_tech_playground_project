package nz.co.test.transactions.ui.compose

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import nz.co.test.transactions.App
import nz.co.test.transactions.R
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.infrastructure.model.Task
import nz.co.test.transactions.ui.AppIcon
import nz.co.test.transactions.ui.AppTheme
import nz.co.test.transactions.ui.states.TaskListViewState
import nz.co.test.transactions.ui.states.TaskViewHolderState
import nz.co.test.transactions.ui.utils.Utility.getFormattedCurrentDate
import nz.co.test.transactions.ui.utils.Utility.makeToast

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun TaskListScreenView(navController: NavController, application: App, viewModel: TaskViewModel) {
    val state by viewModel.state.collectAsState()
    AppTheme(useDarkTheme = application.isDark.value) {
        val systemUiController = rememberSystemUiController()
        val darkIcons = !application.isDark.value
        val statusBarColor = if (darkIcons) Color.White else Color.Black
        SideEffect {
            systemUiController.setSystemBarsColor(statusBarColor, darkIcons = darkIcons)
        }
        TaskListView(
            viewModel,
            state = state,
            navController,
            modifier = Modifier
                .fillMaxSize(),
            onToggleTheme = {
                application.toggleLightTheme()
            }
        )
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun TaskListView(
    viewModel: TaskViewModel,
    state: TaskListViewState,
    navController: NavController,
    modifier: Modifier,
    onToggleTheme: () -> Unit
) {
    val defaultMargin = dimensionResource(
        id = R.dimen.default_margin
    )

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top,
                content = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                } else {
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                }
                            }
                        }, modifier = Modifier
                            .height(20.dp)
                    ) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = "close bottomsheet"
                        )
                    }
                }
            )
            Column(
                content = {
                    Text(
                        text = "Create New",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp
                    )
                    Spacer(modifier = Modifier.padding(16.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        content = {
                            val taskName = remember { mutableStateOf("") }
                            val taskDescription = remember { mutableStateOf("") }

                            // Creating two outlined text fields for
                            // fetching username and password from the user
                            OutlinedTextField(
                                value = taskName.value,
                                onValueChange = { taskName.value = it },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
                                ),
                                label = { Text(text = "Name") },
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = taskDescription.value,
                                onValueChange = { taskDescription.value = it },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.3f)
                                ),
                                label = { Text(text = "Description") },
                                maxLines = 5,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.padding(16.dp))
                            val context = LocalContext.current
                            Button(
                                onClick = {
                                    if (taskName.value.isEmpty() || taskDescription.value.isEmpty()) {
                                        makeToast(context, "fields can't be empty.")
                                    } else {
                                        viewModel.addTask(
                                            Task(
                                                title = taskName.value,
                                                description = taskDescription.value,
                                                date = getFormattedCurrentDate()
                                            )
                                        )
                                        taskName.value = ""
                                        taskDescription.value = ""
                                        coroutineScope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }
//                                        navController.navigate("taskList")
                                    }
                                },
                                Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                            ) {
                                Text("Add")
                            }
                        }
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(16.dp)
            )
        },
        sheetPeekHeight = 0.dp,
        topBar = {}) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "To-Do Manager", style = MaterialTheme.typography.h5, textAlign = TextAlign.Justify)
                    },
                    navigationIcon = {
                        AppIcon(R.drawable.ic_logo, "App Logo", modifier = Modifier.fillMaxSize().padding(12.dp, 12.dp))
                    },
                    actions = {
                        IconButton(onClick = {
                        }) {
                            Icon(Icons.Filled.Search, contentDescription = "")
                        }
                        IconButton(onClick = onToggleTheme) {
                            Icon(Icons.Filled.Build, contentDescription = "")
                        }
                    },
                    elevation = AppBarDefaults.TopAppBarElevation,
                    backgroundColor = MaterialTheme.colors.background
                )
            },
            floatingActionButton = {
                AddFloatingButton(coroutineScope, bottomSheetScaffoldState, navController, modifier)
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,

            bottomBar = {
                val selectedItem = remember { mutableStateOf("delete") }
                BottomAppBar(
                    cutoutShape = RoundedCornerShape(50),
                    content = {
                        BottomNavigation() {
                            BottomNavigationItem(
                                icon = {
                                    Icon(Icons.Filled.Favorite, "")
                                },
                                label = { Text(text = "Favorite") },
                                selected = selectedItem.value == "favorite",
                                onClick = {
                                    selectedItem.value = "favorite"
                                },
                                alwaysShowLabel = false
                            )

                            BottomNavigationItem(
                                icon = {
                                    Icon(Icons.Filled.Delete, "")
                                },
                                label = { Text(text = "Delete All") },
                                selected = selectedItem.value == "delete",
                                onClick = {
                                    viewModel.removeAllTasks()
                                    selectedItem.value = "delete"
                                },
                                alwaysShowLabel = false
                            )
                        }
                    }
                )
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
                        viewModel = viewModel,
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
    viewModel: TaskViewModel,
    modifier: Modifier
) {
    LazyColumn(modifier = modifier) {
        items(data) { taskInfo ->
            TaskViewHolder(
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

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun AddFloatingButton(
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    navController: NavController,
    modifier: Modifier
) {
    FloatingActionButton(
        shape = RoundedCornerShape(50),
        backgroundColor = MaterialTheme.colors.primary,
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
    ) {
        Icon(Icons.Filled.Add, "", tint = MaterialTheme.colors.onPrimary)
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun TaskListPreview() {
    val list: ArrayList<TaskViewHolderState> = ArrayList()
    list.add(
        TaskViewHolderState(
            taskName = "Task Title",
            taskDescription = "Task Description",
            date = "Task Date",
            taskIdentifier = "Identifier"
        )
    )
    AppTheme {
        TaskListView(
            viewModel(),
            state = TaskListViewState.Loaded(list),
            navController = NavController(LocalContext.current),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    all = dimensionResource(
                        id = R.dimen.default_margin
                    )
                ),
            onToggleTheme = {
            }
        )
    }
}