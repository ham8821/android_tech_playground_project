package nz.co.test.transactions.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import nz.co.test.transactions.App
import nz.co.test.transactions.ui.AppTheme
import nz.co.test.transactions.ui.CompletedTaskViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun CompletedTaskListScreenView(navController: NavController, application: App, viewModel: CompletedTaskViewModel) {
    val state by viewModel.state.collectAsState()
    AppTheme(useDarkTheme = application.isDark.value) {
        val systemUiController = rememberSystemUiController()
        val darkIcons = !application.isDark.value
        val statusBarColor = if (darkIcons) Color.White else Color.Black
        SideEffect {
            systemUiController.setSystemBarsColor(statusBarColor, darkIcons = darkIcons)
        }
        Text(text = "This is Completed task dashboard")
    }
}