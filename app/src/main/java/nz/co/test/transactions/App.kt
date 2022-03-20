package nz.co.test.transactions

import com.example.android.roomwordssample.TransactionRoomDatabase
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import nz.co.test.transactions.di.DaggerAppComponent
import nz.co.test.transactions.infrastructure.repository.TransactionsLocalRepository
import nz.co.test.transactions.infrastructure.repository.TransactionsRepository
import nz.co.test.transactions.infrastructure.services.TransactionsService

class App : DaggerApplication() {
    private val applicationInjector = DaggerAppComponent.builder().application(this).build()
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { TransactionRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { TransactionsLocalRepository( database.transactionDao()) }
}
