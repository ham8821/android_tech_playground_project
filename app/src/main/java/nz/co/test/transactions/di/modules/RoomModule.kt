package nz.co.test.transactions.di.modules

import android.app.Application
import android.content.Context
import javax.inject.Singleton

import androidx.room.Room
import com.example.android.roomwordssample.TransactionRoomDatabase
import dagger.Module
import dagger.Provides
import nz.co.test.transactions.infrastructure.dao.TransactionDao
import nz.co.test.transactions.infrastructure.repository.TransactionsLocalRepository


@Module
class RoomModule() {
//    private val demoDatabase: TransactionRoomDatabase
    @Singleton
    @Provides
    fun providesRoomDatabase(mApplication: Application): TransactionRoomDatabase {
        return Room.databaseBuilder(mApplication, TransactionRoomDatabase::class.java, "transaction_database").allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun providesProductDao(demoDatabase: TransactionRoomDatabase): TransactionDao {
        return demoDatabase.transactionDao()
    }

    @Singleton
    @Provides
    fun providesLocalRepository(transactionDao: TransactionDao): TransactionsLocalRepository =
        TransactionsLocalRepository(transactionDao)

    init {
//        demoDatabase =
//            Room.databaseBuilder(mApplication, TransactionRoomDatabase::class.java, "transaction_database").build()
    }
}