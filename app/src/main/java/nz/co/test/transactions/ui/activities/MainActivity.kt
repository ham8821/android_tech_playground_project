package nz.co.test.transactions.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nz.co.test.transactions.ui.compose.TaskListScreenView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = "taskList") {
                composable("taskList") {
                    // option #1 create a component inside NavBackStackEntry,
                    // which can be helpful if we need to provide more than one object from DI here
                   TODO("viewmodel di injection here")
                    TaskListScreenView(viewModel = , navController = navController)
                }
            }
        }
    }

    companion object{
        private const val TAG_FRAGMENT_TRANSACTIONS = "_transactoin_fragment"
    }
}