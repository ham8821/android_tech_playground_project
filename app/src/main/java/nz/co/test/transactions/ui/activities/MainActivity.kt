package nz.co.test.transactions.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import nz.co.test.transactions.TaskViewModel
import nz.co.test.transactions.ui.compose.TaskDetailScreenView
import nz.co.test.transactions.ui.compose.TaskListScreenView

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = "taskList") {
                composable("taskList") {
                    val viewModel = hiltViewModel<TaskViewModel>()
                    TaskListScreenView(navController = navController, viewModel = viewModel)
                }
                composable("taskDetail/{userId}"){ backStackEntry ->
                    val viewModel = hiltViewModel<TaskViewModel>()
                    TaskDetailScreenView(navController, backStackEntry.arguments?.getString("userId"), viewModel)
                }
            }
        }
    }

    companion object{
        private const val TAG_FRAGMENT_TRANSACTIONS = "_transactoin_fragment"
    }
}