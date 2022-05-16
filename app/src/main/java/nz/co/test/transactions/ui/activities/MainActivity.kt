package nz.co.test.transactions.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nz.co.test.transactions.App
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.ui.compose.TaskDetailScreenView
import nz.co.test.transactions.ui.compose.TaskListScreenView
import javax.inject.Inject

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var application: App

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            var canPop by remember { mutableStateOf(false) }

            navController.addOnDestinationChangedListener { controller, _, _ ->
                canPop = controller.previousBackStackEntry != null
            }

            val navigationIcon: (@Composable () -> Unit)? =
                if (canPop) {
                    {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                } else {
                    null
                }
            NavHost(navController, startDestination = "taskList") {
                composable("taskList") {
                    val viewModel = hiltViewModel<TaskViewModel>()
                    TaskListScreenView(navController = navController, application=  application, viewModel = viewModel)
                }
                composable("taskDetail/{userId}"){ backStackEntry ->
                    val viewModel = hiltViewModel<TaskViewModel>()
                    TaskDetailScreenView(navController, backStackEntry.arguments?.getString("userId"), application=  application, viewModel, navigationIcon)
                }
            }
        }
    }

    companion object{
        private const val TAG_FRAGMENT_TRANSACTIONS = "_transactoin_fragment"
    }
}