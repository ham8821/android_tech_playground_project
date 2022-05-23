package nz.co.test.transactions

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import nz.co.test.transactions.infrastructure.TaskRoomDataBase
import nz.co.test.transactions.infrastructure.repository.CompletedTaskLocalRepository
import nz.co.test.transactions.infrastructure.repository.TaskLocalRepository

@HiltAndroidApp
class App : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { TaskRoomDataBase.getDatabase(this, applicationScope) }
    val repository by lazy { TaskLocalRepository( database.taskDao()) }
    val completedRepository by lazy { CompletedTaskLocalRepository( database.completedTaskDao()) }


    //Todo: could be implemented with SharedPreference
    val isDark = mutableStateOf(true)

    override fun onCreate() {
        super.onCreate()
    }

    fun toggleLightTheme(){
        isDark.value = !isDark.value
    }
}
