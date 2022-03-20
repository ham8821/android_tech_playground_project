package nz.co.test.transactions.infrastructure.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import nz.co.test.transactions.infrastructure.model.Transaction

@Dao
interface TransactionDao {
    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM transaction_table ORDER BY id ASC")
    fun retrieveTransactions(): List<Transaction>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Transaction)

    @Query("DELETE FROM transaction_table")
    suspend fun deleteAll()

    @Delete
    fun deleteTransaction(transaction: Transaction)
}