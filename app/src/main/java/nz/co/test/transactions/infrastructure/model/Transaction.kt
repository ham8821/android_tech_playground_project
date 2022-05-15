package nz.co.test.transactions.infrastructure.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "transaction_table")
data class Transaction(
    @SerializedName("id") @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @SerializedName("transactionDate") @ColumnInfo(name = "transactionDate") val transactionDate: String,
    @SerializedName("summary") @ColumnInfo(name = "summary") val summary: String,
    @SerializedName("debit") @ColumnInfo(name = "debit") val debit: String,
    @SerializedName("credit") @ColumnInfo(name = "credit") val credit: String
)